package com.example.api;


public interface Constants {

    String API_V1_VERSION = "v1";
    String API_V1_PREFIX = "/api/" + API_V1_VERSION;
    String API_PRIVATE_PREFIX = API_V1_PREFIX + "/private/";

    String API_V1_PREFIX_DIRECTORY = API_V1_PREFIX + "/directory";
    String API_V1_DIRECTORY_MECHANIC_PREFIX = API_V1_PREFIX_DIRECTORY + "/mechanic";
    String API_V1_DIRECTORY_PROMO_PREFIX = API_V1_PREFIX_DIRECTORY + "/promo";
    String API_V1_DIRECTORY_PROMO_FORMAT_PREFIX = API_V1_PREFIX_DIRECTORY + "/promo/format";
    String API_V1_DIRECTORY_MARKETING_PREFIX = API_V1_PREFIX_DIRECTORY + "/marketing";
    String API_V1_DIRECTORY_INDICATOR_PREFIX = API_V1_PREFIX_DIRECTORY + "/indicator";
    String API_V1_DIRECTORY_KIT_PREFIX = API_V1_PREFIX_DIRECTORY + "/kit";

    String API_V1_MENU_PREFIX = API_V1_PREFIX + "/menu";
    String API_V1_PRODUCT_PREFIX = API_V1_PREFIX + "/product";
    String API_V1_STRUCTURE_PREFIX = API_V1_PREFIX + "/structure";

    String API_V1_PROMO_BUDGET = API_V1_PREFIX + "/promo/budget";
    String API_V1_PROMO_BUDGET_ID = API_V1_PREFIX + "/promo/budget/{id}";
    String API_V1_PROMO_BUDGET_SYNCHRONIZE = API_V1_PREFIX + "/promo/budget/{id}/synchronize";
    String API_V1_PROMO_BUDGET_FUNDING = API_V1_PREFIX + "/promo/budget/funding/{campaignId}";

    String API_V1_PROMO_FORMAT_PREFIX = API_V1_PREFIX + "/promo/format";
    String API_V1_PROMO_STATUS_PREFIX = API_V1_PREFIX + "/promo/status";
    String API_V1_PROMO_CAMPAIGN_PREFIX = API_V1_PREFIX + "/promo/campaign";
    String API_V1_PROMO_ACTIVITY_PREFIX = API_V1_PREFIX + "/promo/activity";

    String API_V1_PROMO_ACTIVITY_GANTT = API_V1_PROMO_ACTIVITY_PREFIX + "/gantt";
    String API_V1_PROMO_ACTIVITY_CALENDAR = API_V1_PROMO_ACTIVITY_PREFIX + "/calendar";
    String API_V1_PROMO_ACTIVITY_ID = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}";
    String API_V1_PROMO_ACTIVITY_STATUS_LOG = API_V1_PROMO_ACTIVITY_PREFIX + "/status/log";

    String API_V1_PROMO_ACTIVITY_INDICATOR = API_V1_PREFIX + "/promo/activity/{activityId}/indicator/";
    String API_V1_PROMO_ACTIVITY_STATUS = API_V1_PREFIX + "/promo/activity/{activityId}/status";
    String API_V1_PROMO_ACTIVITY_VALIDATION = API_V1_PREFIX + "/promo/activity/{activityId}/validation";
    String API_V1_PROMO_ACTIVITY_POSITION_VALIDATION = API_V1_PREFIX + "/promo/activity/{activityId}/position/{positionId}/validation";

    String API_V1_PROMO_ACTIVITY_SCHEDULE = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/schedule/{type}";
    String API_V1_PROMO_ACTIVITY_SCHEDULE_GENERATE = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/schedule/{type}/generate";

    String API_V1_PROMO_KIT = API_V1_PREFIX + "/promo/activity/{activityId}/kit";
    String API_V1_PROMO_KIT_GROUP = API_V1_PREFIX + "/promo/activity/{activityId}/kit/group";
    String API_V1_PROMO_KIT_POSITION = API_V1_PREFIX + "/promo/activity/{activityId}/kit/group/{groupId}/position";
    String API_V1_PROMO_KIT_POSITION_FROM_FIND = API_V1_PREFIX + "/promo/activity/{activityId}/kit/group/{groupId}/position/all";

    String API_V1_PROMO_MARKETING_PREFIX = API_V1_PREFIX + "/promo/marketing/record";
    String API_V1_PROMO_ARTIFACT_PREFIX = API_V1_PREFIX + "/promo/artifact";
    String API_V1_PROMO_ARTIFACT_ACTIVITY_FORMAT_SETTING = API_V1_PROMO_ARTIFACT_PREFIX + "/activity/format/setting";
    String API_V1_PROMO_ARTIFACT_ACTIVITY_FORMAT_SETTING_ID = API_V1_PROMO_ARTIFACT_PREFIX + "/activity/format/setting/{id}";

    String API_V1_PROMO_ARTIFACT_TEMPLATE_PREFIX = API_V1_PREFIX + "/promo/artifact/template/setting";

    String API_V1_PROMO_ARTIFACT_ACTIVITY = API_V1_PROMO_ARTIFACT_PREFIX + "/activity";
    String API_V1_PROMO_ARTIFACT_ACTIVITY_ID = API_V1_PROMO_ARTIFACT_PREFIX + "/activity/{id}";
    String API_V1_PROMO_ARTIFACT_ACTIVITY_GENERATE = API_V1_PROMO_ARTIFACT_PREFIX + "/activity/generate";

    String API_V1_PROMO_PROMOCODE_LINKING = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/promocode/linking";
    String API_V1_PROMO_PROMOCODE_LINKING_AVAILABLE = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/promocode/linking/available";

    String API_V1_PROMO_PROMOCODE_PROVIDE_SETTING = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/promocode/providing";

    String API_V1_PROMO_PROMOCODE_POSITIONS = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/promocode/position/{type}";
    String API_V1_PROMO_PROMOCODE_POSITIONS_FROM_FIND = API_V1_PROMO_PROMOCODE_POSITIONS + "/all";
    String API_V1_PROMO_PROMOCODE_POSITIONS_UPDATE = API_V1_PROMO_PROMOCODE_POSITIONS + "/fields";

    String API_V1_PROMO_ARTIFACT_POSITIONS_FROM_FIND = API_V1_PROMO_ACTIVITY_PREFIX + "/{activityId}/{artifactType}/position/{type}/all";

    String API_V1_BUDGET_TRANSACTION_PREFIX = API_V1_PREFIX + "/budget/transaction";

    String API_V1_PROMO_ASSISTANCE_PREFIX = API_V1_PREFIX + "/promo/assistance";
    String API_V1_PROMO_ORDER_PREFIX = API_V1_PREFIX + "/promo/order";

    String API_V1_PROMO_ACTIVITY_POSITION_ALL = API_V1_PREFIX + "/promo/activity/{activityId}/certificate/position/{type}/all";

    String PATH_HEALTHCHECK = "/healthcheck";

    String X_REQUEST_ID = "X-Request-Id";

}
