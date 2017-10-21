package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * 
 * @author alexgmg
 *
 */
@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3", "value4", "value5");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field4");
		FilterProvider filters = 
				new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		
		return mapping;
	}

	@GetMapping("/filtering-by-field")
	public MappingJacksonValue retrieveSomeBeanFilteredByField(@RequestParam(name="filter") Set<String> filterFields) {
		SomeBean someBean = new SomeBean("value1", "value2", "value3", "value4", "value5");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(filterFields);
		FilterProvider filters = 
				new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		
		return mapping;
	}

	
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue retrieveListOfSomeBean() {
		
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3", "value4", "value5"),
				new SomeBean("value6", "value7", "value8", "value9", "value10"));
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field4");
		FilterProvider filters = 
				new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);

		
		return mapping;
	}

}
