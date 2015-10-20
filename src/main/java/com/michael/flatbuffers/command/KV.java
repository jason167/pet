// automatically generated, do not modify

package com.michael.flatbuffers.command;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

public class KV extends Struct {
	public KV __init(int _i, ByteBuffer _bb) {
		bb_pos = _i;
		bb = _bb;
		return this;
	}

	public long key() {
		return bb.getLong(bb_pos + 0);
	}

	public double value() {
		return bb.getDouble(bb_pos + 8);
	}

	public static int createKV(FlatBufferBuilder builder, long key, double value) {
		builder.prep(8, 16);
		builder.putDouble(value);
		builder.putLong(key);
		return builder.offset();
	}
};
