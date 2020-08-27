package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TCert;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * @author jobob
 * @since 2020-07-24
 */
@Component
public interface TCertMapper  {
    List<TCert> queryCertsByAccttype(Integer accttype);
}
