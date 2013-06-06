package org.lacassandra.smooshyfaces.serializers.astyanax;


import com.netflix.astyanax.serializers.AbstractSerializer;
import com.netflix.astyanax.serializers.IntegerSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BigDecimalSerializer extends AbstractSerializer<BigDecimal> {

    private final static BigDecimalSerializer INSTANCE = new BigDecimalSerializer();

    public static BigDecimalSerializer get() {
        return INSTANCE;
    }

    @Override
    public ByteBuffer toByteBuffer(BigDecimal obj) {
        byte[] unscaledPortion = obj.unscaledValue().toByteArray();
        byte[] scalePortion = IntegerSerializer.get().toBytes(obj.scale());
        byte[] buffer = new byte[unscaledPortion.length + scalePortion.length];
        System.arraycopy(scalePortion, 0, buffer, 0, scalePortion.length);
        System.arraycopy(unscaledPortion, 0, buffer, scalePortion.length, unscaledPortion.length);
        return ByteBuffer.wrap(buffer, 0, buffer.length);
    }

    @Override
    public BigDecimal fromByteBuffer(ByteBuffer byteBuffer) {
        byte[] scalePortion = new byte[4];
        byteBuffer.get(scalePortion, 0, scalePortion.length);
        byte[] unscaledPortion = new byte[byteBuffer.capacity() - scalePortion.length];
        byteBuffer.get(unscaledPortion, 0, unscaledPortion.length);
        return new BigDecimal(new BigInteger(unscaledPortion), IntegerSerializer.get().fromBytes(scalePortion));
    }
}
