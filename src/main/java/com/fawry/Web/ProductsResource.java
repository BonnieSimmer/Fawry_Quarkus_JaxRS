package com.fawry.Web;

import com.fawry.StoreManager.Product;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import static com.fawry.Repository.ProductRepository.*;

import java.net.URI;

@Path("/")
public class ProductsResource {
    @Inject
    @Location("ProductsResource/products.html")
    Template productsTemplate;
    @Inject
    @Location("ProductsResource/product-form.html")
    Template productFormTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listProducts() {
        return productsTemplate.data("products", getInstance().getProducts());
    }

    @GET
    @Path("/form")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getForm(@QueryParam("id") Integer id) {
        Product product = (id != null)? getInstance().getProduct(id): new Product();
        String action = (id != null) ? ("/" + id) : "/";
        return productFormTemplate
                .data("product", product)
                .data("action", action)
                .data("isUpdate", id != null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addProduct(@FormParam("name") String name,
                               @FormParam("price") double price,
                               @FormParam("quantity") int quantity) {
        getInstance().addProduct(name,price,quantity);
        return Response.seeOther(URI.create("/")).build();
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateProduct(@PathParam("id") int id,
                                  @FormParam("name") String name,
                                  @FormParam("price") double price,
                                  @FormParam("quantity") int quantity) {
        getInstance().updateProduct(id, name, price, quantity);
        return Response.seeOther(URI.create("/")).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        getInstance().deleteProduct(id);
        return Response.seeOther(URI.create("/")).build();
    }
}
