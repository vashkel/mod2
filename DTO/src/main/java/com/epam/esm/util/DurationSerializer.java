package com.epam.esm.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends StdSerializer<Duration> {


    protected DurationSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider provider) throws IOException {
       gen.writeNumber(value.toDays());
    }
}
