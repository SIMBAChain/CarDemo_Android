package com.example.stevenperegrine.simba_cardemo.PostClasses;

import java.util.Map;

public class Response {







    private  String id;
    private Map payload;
    private String timestamp;
    private  String smart_contract_id;
    private String bytes_store_on_blockchain;
    private Void bundle_id;
    private String adapter_id;
    private  String application_id;
    private  String data_store_id;
    private  String method_id;
    private  String organisation_id;
    private  Void parent_id;
    private  Integer user_id;
    private   Boolean is_asset;
    private   Void receipt;
    private Void group_id;
    private  Void member_id;
    private Void error;
    private Void transaction_hash;
    private String bytes_store_on_datastore;


    public Response(String id,
                    Map payload,
                    String timestamp,
                    String smart_contract_id,
                    String bytes_store_on_blockchain,
                    Void bundle_id,
                    String bytes_stored_on_datastore,
                    String adapter_id,
                    String application_id,
                    String data_store_id,
                    String method_id,
                    String organisation_id,
                    Void parent_id,
                    Integer user_id,
                    Boolean is_asset,
                    Void receipt,
                    Void group_id,
                    Void member_id,
                    Void error,
                    Void transaction_hash) {

                            this.id = id;
                            this.payload = payload;
                            this.timestamp = timestamp;
                            this.smart_contract_id = smart_contract_id;
                            this.bytes_store_on_blockchain = bytes_store_on_blockchain;
                            this.bundle_id = bundle_id;
                            this.bytes_store_on_datastore = bytes_stored_on_datastore;
                            this.adapter_id = adapter_id;
                            this.application_id = application_id;
                            this.data_store_id = data_store_id;
                            this.method_id = method_id;
                            this.organisation_id = organisation_id;
                            this.parent_id = parent_id;
                            this.user_id = user_id;
                            this.is_asset = is_asset;
                            this.receipt = receipt;
                            this.group_id = group_id;
                            this.member_id = member_id;
                            this.error = error;
                            this.transaction_hash = transaction_hash;

                     }








}

