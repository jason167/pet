package com.michael.ketama;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class KetamaImpl {
	private Logger logger = LoggerFactory.getLogger(KetamaImpl.class);
	private int NUM_REPS;
	private transient volatile TreeMap<Long, List<TcpSession>> ketamaSessions = new TreeMap<Long, List<TcpSession>>();
	private final HashAlgorithm hashAlg;
	private volatile int maxTries;
	private final Random random = new Random();
	
	protected boolean failureMode;

	public void setFailureMode(boolean failureMode) {
		this.failureMode = failureMode;

	}
	
	public KetamaImpl(int num_peps) {
		// TODO Auto-generated constructor stub
		Assert.isTrue(num_peps % 8 == 0, "must be multiple of 8");
		this.NUM_REPS = num_peps;
		this.hashAlg = HashAlgorithm.KETAMA_HASH;
	}
	
	public KetamaImpl() {
		// TODO Auto-generated constructor stub
		this(16);
	}
	
	public final TcpSession getSessionByKey(final String key) {
		if (this.ketamaSessions == null || this.ketamaSessions.size() == 0) {
			return null;
		}
		long hash = this.hashAlg.hash(key);
		TcpSession rv = this.getSessionByHash(hash);
		int tries = 0;
		while (!this.failureMode && (rv == null || rv.isClosed())
				&& tries++ < this.maxTries) {
			hash = this.nextHash(hash, key, tries);
			rv = this.getSessionByHash(hash);
		}
		return rv;
	}
	
	public final TcpSession getSessionByHash(final long hash) {
		TreeMap<Long, List<TcpSession>> sessionMap = this.ketamaSessions;
		if (sessionMap.size() == 0) {
			return null;
		}
		Long resultHash = hash;
		if (!sessionMap.containsKey(hash)) {
			// Java 1.6 adds a ceilingKey method, but xmemcached is compatible
			// with jdk5,So use tailMap method to do this.
			SortedMap<Long, List<TcpSession>> tailMap = sessionMap.tailMap(hash);
			if (tailMap.isEmpty()) {
				resultHash = sessionMap.firstKey();
			} else {
				resultHash = tailMap.firstKey();
			}
		}
		//
		// if (!sessionMap.containsKey(resultHash)) {
		// resultHash = sessionMap.ceilingKey(resultHash);
		// if (resultHash == null && sessionMap.size() > 0) {
		// resultHash = sessionMap.firstKey();
		// }
		// }
		List<TcpSession> sessionList = sessionMap.get(resultHash);
		if (sessionList == null || sessionList.size() == 0) {
			return null;
		}
		int size = sessionList.size();
		return sessionList.get(this.random.nextInt(size));
	}

	public final long nextHash(long hashVal, String key, int tries) {
		long tmpKey = this.hashAlg.hash(tries + key);
		hashVal += (int) (tmpKey ^ tmpKey >>> 32);
		hashVal &= 0xffffffffL; /* truncate to 32-bits */
		return hashVal;
	}

	public void updateSessions(final Collection<TcpSession> list, HashAlgorithm alg) {
		TreeMap<Long, List<TcpSession>> sessionMap = new TreeMap<Long, List<TcpSession>>();
		int numReps = NUM_REPS;
		for (TcpSession session : list) {
			numReps *= session.getWeight() > 0 ? session.getWeight() : 1;
			if (alg == HashAlgorithm.KETAMA_HASH) {
				for (int i = 0; i < numReps / 4; i++) {
					byte[] digest = HashAlgorithm.computeMd5(session.getSockStr() + "-" + i);
					for (int h = 0; h < 4; h++) {
						long k = (long) (digest[3 + h * 4] & 0xFF) << 24 | (long) (digest[2 + h * 4] & 0xFF) << 16
								| (long) (digest[1 + h * 4] & 0xFF) << 8 | digest[h * 4] & 0xFF;
						this.getSessionList(sessionMap, k).add(session);
					}

				}
			}else{
				for (int i = 0; i < numReps; i++) {
					for (int j = 0; j < numReps; j++) {
						long key = alg.hash(session.getSockStr() + "-" + j);
						this.getSessionList(sessionMap, key).add(session);
					}
				}
			}
			
		}
		
		logger.info("session size:{}, avg:{}", sessionMap.size(), sessionMap.size() / list.size());
		this.ketamaSessions = sessionMap;
		this.maxTries = list.size();
		
	}

	private List<TcpSession> getSessionList(TreeMap<Long, List<TcpSession>> sessionMap, long k) {
		List<TcpSession> sessionList = sessionMap.get(k);
		if (sessionList == null) {
			sessionList = new ArrayList<TcpSession>();
			sessionMap.put(k, sessionList);
		}
		return sessionList;
	}

}
