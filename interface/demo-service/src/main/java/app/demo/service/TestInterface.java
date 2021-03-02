package app.demo.service;

import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;

/**
 * @author steve
 */
public interface TestInterface {
    @GET
    @Path("/test")
    TestResponse test();
}
