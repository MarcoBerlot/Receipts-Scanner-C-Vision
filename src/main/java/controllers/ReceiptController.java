package controllers;

import api.CreateReceiptRequest;
import api.CreateTagRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;

    public ReceiptController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @POST
    public int createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        return receipts.insert(receipt.merchant, receipt.amount);
    }

    @GET
    public List<ReceiptResponse> getReceipts() {
        List<ReceiptsRecord> receiptRecords = receipts.getAllReceipts();
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
    @PUT
    @Path("/tags/{tag}")
    public String toggleTag(@PathParam("tag") String tagName,@Valid @NotNull CreateTagRequest tagRequest) {
        receipts.insert_tag(tagName,tagRequest.receipt_id);
        return tagName;
    }
    @GET
    @Path("/tags/")
    public List<ReceiptResponse> getTaggedReceipts() {
        List<ReceiptsRecord> receiptTagRecords = receipts.getAllTags();
        return receiptTagRecords.stream().map(ReceiptResponse::new).collect(toList());
    }


}
