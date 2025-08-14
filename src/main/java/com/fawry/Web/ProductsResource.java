package com.fawry.Web;

import com.fawry.StoreManager.Product;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import static com.fawry.Repository.ProductRepository.*;

import java.util.Collection;

@Path("/")
public class ProductsResource {

    @CheckedTemplate
    class Templates {
        static native TemplateInstance products(Collection<Product> products);
    }

    @GET
    public Response getProducts() {
        TemplateInstance ProductsTemplate = Templates.products(getInstance().getProducts());
        return Response.ok(ProductsTemplate).build();
    }
}
