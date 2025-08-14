package com.fawry.Web;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class GlobalErrorHandler implements ExceptionMapper<Throwable> {
    @Inject
    @Location("ProductsResource/error.html")
    Template errorTemplate;

    @Override
    public Response toResponse(Throwable exception) {
        TemplateInstance page = errorTemplate.data("message", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(page.render())
                .build();
    }
}
