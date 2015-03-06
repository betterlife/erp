/**
 * Created by larry on 15/2/28.
 */
var typeHeadService = function ($rootScope, $http) {
    var internal = {};

    /**
     * Set the base object field upon user select any object from popup list
     * @param $item the item, contains description(represent by represent field) and raw object data
     * @param $model, equal to the description(not sure what it means)
     * @param $label, equal to the description(not sure what it means)
     * @param baseObjectFieldName, field name, used to set corresponding field in entity object.
     */
    internal.onTypeHeadSelect = function($item, $model, $label, baseObjectFieldName) {
        return $item.raw;
    };

    /**
     * raw contains the raw data.
     * @param entityType Entity Type
     * @param representField Represent field
     * @param val, the current user input value.
     * @returns {*|!Promise.<RESULT>}
     * description contains the UI display label
     * raw contains the raw json object which will be set back to the entity.
     */
    internal.getBaseObjects = function(entityType, representField, val) {
        return $http.get('/rest/q/' + entityType, {
            params: { keyword: val }
        }).then(function(response){
            return response.data.result.map(function (item) {
                return {
                    "description": item[representField],
                    "raw": item
                };
            });
        });
    };

    internal.refreshOptions = function(fieldEntityType, representField, fieldName, currentVal){
        $http.get("/rest/" + fieldEntityType, {}).success(function (entityData) {
            var selectElem = $('#' + fieldName);
            selectElem.find('option').remove().end();
            var data = entityData.result;
            $('<option>').val('? undefined:undefined ?').text("").appendTo(selectElem);
            for(var idx = 0; idx < data.length; idx++){
                var item = data[idx];
                var option = $('<option>');
                option.val(item.id).text(item[representField]).appendTo(selectElem);
            }
            selectElem.val(currentVal);
        })
    };

    return internal;
};

typeHeadService.$inject = ['$rootScope', '$http'];

angular.module('mainApp').factory('typeHeadService', typeHeadService);