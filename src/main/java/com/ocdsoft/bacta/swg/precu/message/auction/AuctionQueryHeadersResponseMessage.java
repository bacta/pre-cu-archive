package com.ocdsoft.bacta.swg.precu.message.auction;

/**
 * Created by crush on 8/13/2014.
 */
public class AuctionQueryHeadersResponseMessage {

    //SearchConditionComparison
    private static final int SCC_int = 0x0;
    private static final int SCC_float = 0x1;
    private static final int SCC_string_equal = 0x2;
    private static final int SCC_string_not_equal = 0x3;
    private static final int SCC_string_contain = 0x4;
    private static final int SCC_string_not_contain = 0x5;
    private static final int SCC_LAST = 0x6;

    //AuctionSearchType
    private static final int AST_ByCategory = 0x0;
    private static final int AST_ByLocation = 0x1;
    private static final int AST_ByAll = 0x2;
    private static final int AST_ByPlayerSales = 0x3;
    private static final int AST_ByPlayerBids = 0x4;
    private static final int AST_ByPlayerStockroom = 0x5;
    private static final int AST_ByVendorOffers = 0x6;
    private static final int AST_ByVendorSelling = 0x7;
    private static final int AST_ByVendorStockroom = 0x8;
    private static final int AST_ByPlayerOffersToVendor = 0x9;

    //AuctionLocationSearch
    private static final int ALS_Galaxy = 0x0;
    private static final int ALS_Planet = 0x1;
    private static final int ALS_Region = 0x2;
    private static final int ALS_Market = 0x3;

    //AdvancedSearchMatchAllAny
    private static final int ASMAA_match_all = 0x0;
    private static final int ASMAA_match_any = 0x1;
    private static final int ASMAA_not_match_all = 0x2;
    private static final int ASMAA_not_match_any = 0x3;
    private static final int ASMAA_LAST = 0x4;

    //int requestId
    //int typeFlag
    //string[] stringPalette
    //UnicodeString[] wideStringPalette
    //PalettizedItemDataHeader[] palettizedAuctionData
    //List<ItemDataHeader> auctionData
    //unsigned short queryOffset
    //bool hasMorePages
}
