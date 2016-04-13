package com.michael.zk.zkclient;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.imps.DefaultACLProvider;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class ZkClient implements Client {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private final int CONNECTION_TIMEOUT_MS = 10 * 1000;
	private final int SLEEP_MS_BETWEEN_RETRIES = 5000;
	private final int SESSION_TIMEOUT_MS = 20 * 1000;
	private final String ROOT = "/configurer";

	private String url;
	private String child;
	private CuratorFramework client;
	IZkChildHandler childHandler;
	ExecutorService executor;
	Watcher watcher;
	String fullPath;
	PathChildrenCache childrenCache;
	

	public ZkClient(IZkChildHandler childHandler, String url, String path) throws NoSuchAlgorithmException {
		// TODO Auto-generated constructor stub
		this.childHandler = childHandler;
		this.url = url;
		this.child = path;
		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				// TODO Auto-generated method stub
				Thread thread = new Thread(r, "zkClient-%d");
				thread.setDaemon(true);
				return thread;
			}
		});
		
		// 监听父节点的观察者
        watcher = new Watcher() {

            @Override
            public void process(final WatchedEvent event) {
                try {
                	executor.execute(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								byte[] buff = client.getData().storingStatIn(new Stat()).forPath(event.getPath());
								
								String str = "null";
								if (buff != null) {
									str = new String(buff);
								}
								logger.info("event type :{}, path:{}, data:{}", 
												new Object[]{event.getType(), event.getPath(), str});
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
				} 
                finally{
                	try {
						client.checkExists().usingWatcher(watcher).forPath(ZKPaths.makePath(ROOT, child));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        };
        
		init();
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		final java.util.concurrent.CountDownLatch counter = new CountDownLatch(1);
		ConnectionStateListener connectionStateListener = new ConnectionStateListener() {

			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				// TODO Auto-generated method stub
				logger.info("conn state:{}",newState);
				if (newState == ConnectionState.CONNECTED) {
					counter.countDown();
				}
			}
		};

		client.getConnectionStateListenable().addListener(connectionStateListener);
		client.start();
		try {
			counter.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return;
		}

		client.getConnectionStateListenable().removeListener(connectionStateListener);
		
		// 监听节点变化
		fullPath = ZKPaths.makePath(ROOT, child);
		client.checkExists().usingWatcher(watcher).forPath(fullPath);
		
		final NodeCache nodeCache = new NodeCache(client, fullPath);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			
			@Override
			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				ChildData currentData = nodeCache.getCurrentData();
				String str = "null";
				if (currentData.getData() != null) {
					str = new String(currentData.getData());
				}
				logger.info("node changed ... path:{}, data:{}, stat:{}",
								new Object[]{currentData.getPath(), str, currentData.getStat()});
			}
		}, executor);
		
		nodeCache.start(true);
		
		final PathChildrenCache childrenCache = new PathChildrenCache(client, fullPath, true);
		childrenCache.getListenable().addListener(new PathChildrenCacheListener() {

			public void childEvent(CuratorFramework client, final PathChildrenCacheEvent event) throws Exception {
				// TODO Auto-generated method stub
				executor.execute(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							childHandler.handler(event, childrenCache.getCurrentData());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});
		try {
			childrenCache.start(StartMode.BUILD_INITIAL_CACHE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if (client != null) {
			client.close();
			client = null;
		}
	}

	private void init() throws NoSuchAlgorithmException {
		Builder builder = CuratorFrameworkFactory
				.builder()
				.connectString(url)
				.connectionTimeoutMs(CONNECTION_TIMEOUT_MS);
		builder = builder.retryPolicy(
				new RetryNTimes(Integer.MAX_VALUE, SLEEP_MS_BETWEEN_RETRIES))
				.sessionTimeoutMs(SESSION_TIMEOUT_MS);
		client = builder.aclProvider(new _DefaultACLProvider()).build();
	}
	
	class _DefaultACLProvider extends DefaultACLProvider{
		
		private Map<String, List<ACL>> acls = Maps.newHashMap();
		public _DefaultACLProvider() {
			// TODO Auto-generated constructor stub
		}
		
		public _DefaultACLProvider(String path, String uid, String passwd) throws NoSuchAlgorithmException {
			// TODO Auto-generated constructor stub
			String idPassword = uid + ':' + passwd;
            Id id = new Id("digest", DigestAuthenticationProvider.generateDigest(idPassword));
            ACL acl = new ACL(Perms.ALL, id);
            acls.put(path, Lists.newArrayList(acl));
		}
		
		@Override
		public List<ACL> getAclForPath(String path) {
			// TODO Auto-generated method stub
			List<ACL> acl = acls.get(path);
			if (acl != null) {
				return acl;
			}
			return super.getDefaultAcl();
		}
	}

	@Override
	public ChildData get(String path) {
		// TODO Auto-generated method stub
		String fullPath = ZKPaths.makePath(ROOT, path);
		return childrenCache.getCurrentData(fullPath);
	}

	@Override
	public void put(String path, String data) throws Exception {
		// TODO Auto-generated method stub
		String fullPath = ZKPaths.makePath(ROOT, path);
		Stat stat = client.checkExists().forPath(fullPath);
		if (stat == null) {
//			client.create().creatingParentsIfNeeded().forPath(fullPath, data.getBytes());
			client.getZookeeperClient().getZooKeeper()
				.create(fullPath, data.getBytes(), new _DefaultACLProvider().getDefaultAcl(), CreateMode.PERSISTENT);
		}else{
			client.setData().forPath(fullPath, data.getBytes());
		}
	}

}
