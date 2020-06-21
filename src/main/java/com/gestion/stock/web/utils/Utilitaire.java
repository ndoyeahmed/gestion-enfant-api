package com.gestion.stock.web.utils;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

@Component
public class Utilitaire {

    public MappingJacksonValue getFilter(Object o, String filterName, String propertyName) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertyName);

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter(filterName, filter);

        MappingJacksonValue filtres = new MappingJacksonValue(o);

        filtres.setFilters(listDeNosFiltres);

        return filtres;
    }
}
