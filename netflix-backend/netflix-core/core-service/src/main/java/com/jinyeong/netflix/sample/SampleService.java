package com.jinyeong.netflix.sample;

import com.jinyeong.netflix.sample.response.SampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService implements SearchSampleUseCase {
    private final SamplePort samplePort;

    @Override
    public SampleResponse getSample() {
        SamplePortResponse samplePortResponse = samplePort.getSample();
        return new SampleResponse(samplePortResponse.getName());
    }
}
