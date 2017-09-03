package controllers;

import api.CreateReceiptRequest;
import api.CreateTagRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import io.dropwizard.jersey.sessions.Session;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final ReceiptDao receipts;

    public TagController(ReceiptDao receipts) {
        this.receipts = receipts;
    }
    // You can specify additional @Path steps; they will be relative
    // to the @Path defined at the class level
    @GET
    @Path("/netid")
    public String netId() {
        return "mb2589" ;
    }
    @PUT
    @Path("/tags/{tag}")
    public String toggleTag(@PathParam("tag") String tagName,int id) {
        receipts.insert_tag(tagName,id);
        return tagName;
    }
    @GET
    @Path("/tags/")
    public List<ReceiptResponse> getTaggedReceipts() {
        List<ReceiptsRecord> receiptTagRecords = receipts.getAllTags();
        return receiptTagRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}

