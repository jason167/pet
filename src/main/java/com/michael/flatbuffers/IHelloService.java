package com.michael.flatbuffers;

import java.nio.ByteBuffer;

import com.michael.flatbuffers.command.TestObj;

public interface IHelloService {

	TestObj send(ByteBuffer _bb);
}
