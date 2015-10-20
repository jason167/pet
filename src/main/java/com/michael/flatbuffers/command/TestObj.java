// automatically generated, do not modify

package com.michael.flatbuffers.command;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

public class TestObj extends Table {
	public static TestObj getRootAsTestObj(ByteBuffer _bb) {
		return getRootAsTestObj(_bb, new TestObj());
	}

	public static TestObj getRootAsTestObj(ByteBuffer _bb, TestObj obj) {
		_bb.order(ByteOrder.LITTLE_ENDIAN);
		return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb));
	}

	public TestObj __init(int _i, ByteBuffer _bb) {
		bb_pos = _i;
		bb = _bb;
		return this;
	}

	public long id() {
		int o = __offset(4);
		return o != 0 ? bb.getLong(o + bb_pos) : 0;
	}

	public String name() {
		int o = __offset(6);
		return o != 0 ? __string(o + bb_pos) : null;
	}

	public ByteBuffer nameAsByteBuffer() {
		return __vector_as_bytebuffer(6, 1);
	}

	public int flag() {
		int o = __offset(8);
		return o != 0 ? bb.get(o + bb_pos) & 0xFF : 0;
	}

	public long list(int j) {
		int o = __offset(10);
		return o != 0 ? bb.getLong(__vector(o) + j * 8) : 0;
	}

	public int listLength() {
		int o = __offset(10);
		return o != 0 ? __vector_len(o) : 0;
	}

	public ByteBuffer listAsByteBuffer() {
		return __vector_as_bytebuffer(10, 8);
	}

	public KV kv() {
		return kv(new KV());
	}

	public KV kv(KV obj) {
		int o = __offset(12);
		return o != 0 ? obj.__init(o + bb_pos, bb) : null;
	}

	public Stat stat() {
		return stat(new Stat());
	}

	public Stat stat(Stat obj) {
		int o = __offset(14);
		return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null;
	}

	public long fromInclude() {
		int o = __offset(16);
		return o != 0 ? bb.getLong(o + bb_pos) : 0;
	}

	public byte anyObjType() {
		int o = __offset(18);
		return o != 0 ? bb.get(o + bb_pos) : 0;
	}

	public Table anyObj(Table obj) {
		int o = __offset(20);
		return o != 0 ? __union(obj, o) : null;
	}

	public static void startTestObj(FlatBufferBuilder builder) {
		builder.startObject(9);
	}

	public static void addId(FlatBufferBuilder builder, long id) {
		builder.addLong(0, id, 0);
	}

	public static void addName(FlatBufferBuilder builder, int nameOffset) {
		builder.addOffset(1, nameOffset, 0);
	}

	public static void addFlag(FlatBufferBuilder builder, int flag) {
		builder.addByte(2, (byte) (flag & 0xFF), 0);
	}

	public static void addList(FlatBufferBuilder builder, int listOffset) {
		builder.addOffset(3, listOffset, 0);
	}

	public static int createListVector(FlatBufferBuilder builder, long[] data) {
		builder.startVector(8, data.length, 8);
		for (int i = data.length - 1; i >= 0; i--)
			builder.addLong(data[i]);
		return builder.endVector();
	}

	public static void startListVector(FlatBufferBuilder builder, int numElems) {
		builder.startVector(8, numElems, 8);
	}

	public static void addKv(FlatBufferBuilder builder, int kvOffset) {
		builder.addStruct(4, kvOffset, 0);
	}

	public static void addStat(FlatBufferBuilder builder, int statOffset) {
		builder.addOffset(5, statOffset, 0);
	}

	public static void addFromInclude(FlatBufferBuilder builder,
			long fromInclude) {
		builder.addLong(6, fromInclude, 0);
	}

	public static void addAnyObjType(FlatBufferBuilder builder, byte anyObjType) {
		builder.addByte(7, anyObjType, 0);
	}

	public static void addAnyObj(FlatBufferBuilder builder, int anyObjOffset) {
		builder.addOffset(8, anyObjOffset, 0);
	}

	public static int endTestObj(FlatBufferBuilder builder) {
		int o = builder.endObject();
		return o;
	}

	public static void finishTestObjBuffer(FlatBufferBuilder builder, int offset) {
		builder.finish(offset);
	}
};
