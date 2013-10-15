package com.app.visitor.counter.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.app.datastore.SimpleDataStore;
import com.app.visitor.counter.service.VisitorLocationService;
import com.app.visitor.counter.service.bean.VisitorLocation;

@Controller
public class MapController {

    @Autowired
    VisitorLocationService  visitorLocationService;

    @Autowired
    private SimpleDataStore simpleDataStore;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getPages() {

        ModelAndView model = new ModelAndView("home");
        Set<Object> keys = simpleDataStore.keys();
        Map<Object, Object> allCounters = new HashMap<Object, Object>();
        for (Object key : keys) {
            allCounters.put(key, simpleDataStore.get(key));
        }
        model.addObject("allCounters", allCounters);
        return model;

    }

    @RequestMapping(value = "/getLocation", method = RequestMethod.GET)
    public @ResponseBody
    String getDomainInJsonFormat(@RequestParam String ipAddress) {

        ObjectMapper mapper = new ObjectMapper();

        VisitorLocation location = visitorLocationService.getLocation(ipAddress);

        String result = "";

        try {
            result = mapper.writeValueAsString(location);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;

    }

    @RequestMapping(value = "/page1", method = RequestMethod.GET)
    public ModelAndView page1() {

        ModelAndView model = new ModelAndView("page1");
        return model;
    }
    
    @RequestMapping(value = "/page2", method = RequestMethod.GET)
    public ModelAndView page2() {

        ModelAndView model = new ModelAndView("page2");
        return model;
    }
    
    @RequestMapping(value = "/page3", method = RequestMethod.GET)
    public ModelAndView page3() {

        ModelAndView model = new ModelAndView("page3");
        return model;
    }
    
    @RequestMapping(value = "/page4", method = RequestMethod.GET)
    public ModelAndView page4() {

        ModelAndView model = new ModelAndView("page4");
        return model;
    }

}
