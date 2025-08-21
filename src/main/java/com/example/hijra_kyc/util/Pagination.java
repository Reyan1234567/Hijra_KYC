package com.example.hijra_kyc.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class Pagination {
    public <T> List<T> paginate(int pageNumber, int pageSize, List<T> list) {

        if (pageNumber <= 0 || pageSize <= 0) {
            return Collections.emptyList();
        }
        int index = (pageNumber - 1) * pageSize;
        if (index >= list.size()) {
            return Collections.emptyList();
        }
        if (index + pageSize > list.size()) {
            return list.subList(index, list.size());
        }
        log.info("THE SUBLIST");
//        log.info(String.valueOf(list.subList(index, index + pageSize)));
        return list.subList(index, index + pageSize);
    }
}
