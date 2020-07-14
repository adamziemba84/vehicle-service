package com.azet.vehicle.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCriteriaBuilder {
    public static List<SearchCriteria> build(String searchCriteria) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(searchCriteria + ",");

        List<SearchCriteria> criteriaList = new ArrayList<>();

        while (matcher.find()) {
            criteriaList.add(new SearchCriteria(
                    matcher.group(1),
                    SearchOperation.of(matcher.group(2)),
                    matcher.group(3)));
        }

        return criteriaList;
    }
}
