var context_root = "/";

function jump_to_detail_page() {
    var objectType = $("#object_type").val();
    var objectId = $("#object_id").val();
    var url = context_root + '/rest/' + objectType + "/" + objectId;
    window.open(url);
}

function update_driver_link(){
    id = $("#driver_id").val();
    if (id != undefined && id != null && id != "") {
        link = context_root + "/rest/driver/" + id;
        $("#driver_detail").html("<a href='" + link + "' target='_blank'>See detail of driver " + id + "</a>");
    } else {
        $("#driver_detail").text("");
    }
}