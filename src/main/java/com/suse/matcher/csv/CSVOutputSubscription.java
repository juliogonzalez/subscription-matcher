package com.suse.matcher.csv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A subscription as represented in a CSV output file.
 */
public class CSVOutputSubscription {

    /** Header for the CSV output. */
    public static final String[] CSV_HEADER = {"Subscription ID", "Part Number", "Product Description", "Available Quantity", "Matched Quantity",
            "Start Date (UTC)", "End Date (UTC)"};

    /** The subscription id. */
    private Long id;

    /** The part number. */
    private String partNumber;

    /** The subscription name. */
    private String name;

    /** The quantity. */
    private Integer quantity;

    /** Number of subscriptions matched. */
    private int matched;

    /** The start date. */
    private Date startsAt = new Date(Long.MIN_VALUE);

    /** The end date. */
    private Date expiresAt = new Date(Long.MAX_VALUE);

    /**
     * Instantiates a new CSV output subscription.
     *
     * @param idIn the id
     * @param partNumberIn the part number
     * @param nameIn the name
     * @param quantityIn the quantity
     * @param startsAtIn the start date
     * @param expiresAtIn the end date
     */
    public CSVOutputSubscription(Long idIn, String partNumberIn, String nameIn, Integer quantityIn, Date startsAtIn, Date expiresAtIn) {
        id = idIn;
        partNumber = partNumberIn;
        name = nameIn;
        quantity = quantityIn;
        startsAt = startsAtIn;
        expiresAt = expiresAtIn;

        matched = 0;
    }

    /**
     * Increase the count of matched subscriptions of this type.
     * @param count the count
     */
    public void increaseMatchCount(int count) {
        matched = matched + count;
    }

    /**
     * Gets the CSV row.
     * @return the CSV row
     */
    public List<String> getCSVRow() {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(id));
        row.add(partNumber);
        row.add(name);
        row.add(String.valueOf(quantity));
        row.add(String.valueOf(matched));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        row.add(df.format(startsAt));
        row.add(df.format(expiresAt));
        return row;
    }
}