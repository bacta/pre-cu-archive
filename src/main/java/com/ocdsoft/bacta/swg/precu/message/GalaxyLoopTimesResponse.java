package com.ocdsoft.bacta.swg.precu.message;

import com.ocdsoft.bacta.swg.network.swg.message.SwgMessage;

public class GalaxyLoopTimesResponse extends SwgMessage {
    public GalaxyLoopTimesResponse(long currentFrameMilliseconds, long lastFrameMilliseconds) {
        super(0x03, 0x4E428088);

        writeLong(currentFrameMilliseconds);
        writeLong(lastFrameMilliseconds);
    }
}
