package dao;

import api.ReceiptResponse;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.ReceiptTagRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.Record;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.RECEIPT_TAG;


public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }


    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }
    public int insert_tag(String tag, int id) {
        ReceiptTagRecord receiptTagRecord = dsl
                .insertInto(RECEIPT_TAG, RECEIPT_TAG.RECEIPT_ID, RECEIPT_TAG.TAG)
                .values(id, tag)
                .returning(RECEIPTS.ID)
                .fetchOne();


        return 2;
    }

    public List<ReceiptsRecord> getAllReceipts() {
        return dsl.selectFrom(RECEIPTS).fetch();
    }
    public List<ReceiptsRecord> getAllTags() {
     return dsl .selectDistinct()
                .from(RECEIPTS)
                .rightOuterJoin(RECEIPT_TAG)
                .on(RECEIPT_TAG.RECEIPT_ID.equal(RECEIPTS.ID))
                .fetchInto(RECEIPTS);
    }

}
