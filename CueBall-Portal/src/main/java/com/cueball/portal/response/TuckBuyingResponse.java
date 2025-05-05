package com.cueball.portal.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TuckBuyingResponse<T extends GenericResponse> extends GenericResponse{
    public TuckBuyingResponse(GenericResponse genResp) {
        super(genResp);
    }
}
