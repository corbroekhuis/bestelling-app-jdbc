package com.warehouse;

import com.capgemini.plugin.createbackend.CreateBackend;
import com.capgemini.plugin.createbackend.helper.ControllerHelper;
import com.capgemini.plugin.createbackend.helper.Helper;
import com.capgemini.plugin.createbackend.helper.HtmlHelper;
import com.capgemini.plugin.createbackend.helper.JavascriptHelper;
import com.capgemini.plugin.createbackend.helper.MvcHelper;
import com.warehouse.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CreateApi {

    public static void main(String[] args) {
        CreateBackend createBackend = new CreateBackend();

        List<Helper> helpers = new ArrayList<>();
//        helpers.add( new RepositoryHelper());
//        helpers.add( new ServiceHelper());
        helpers.add( new ControllerHelper());
//        helpers.add( new JavascriptHelper());
//        helpers.add( new HtmlHelper());
//        helpers.add( new MvcHelper());

        createBackend.createBackendFromClass(Order.class, Order.class, helpers);

    }
}
