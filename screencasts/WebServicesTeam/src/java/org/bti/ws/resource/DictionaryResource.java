package org.bti.ws.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import org.bti.ws.model.Word;
import org.bti.ws.persistence.InMemoryDictionary;

/**
 * A RESTful dictionary service.
 */
@Path("/dictionary")
public class DictionaryResource {
    private static InMemoryDictionary dictionary = InMemoryDictionary.instance();

    @GET
    @Path("{word}")
    @Produces({"application/xml", "application/json"})
    public Response get(@PathParam("word") String name)
    {
        Word word = dictionary.getWord(name);
        if(word == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(word).build();
    }

    @PUT
    @Path("{word}")
    @Consumes({"application/xml", "application/json"})
    public Response put(JAXBElement<Word> wordElement)
    {
        dictionary.putWord(wordElement.getValue());
        return Response.ok().build();
    }

    @DELETE
    @Path("{word}")
    public Response delete(@PathParam("word") String name)
    {
        dictionary.removeWord(name);
        return Response.ok().build();
    }
}
