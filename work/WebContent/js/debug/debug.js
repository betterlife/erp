var context_root = "";

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

angular.module('debugApp', ['ngRoute', 'ngCookies'])
    .filter('to_trusted', ['$sce', function ($sce) {
        return function (text) {
            return $sce.trustAsHtml(text);
        };
    }])
    .controller('showEntityMetaCtrl', function($scope, $http) {
        $scope.showEntityMeta = function(){
            $http.get('/rest/entity/' + $scope.entityName).success(function(data){
                $scope.entityMetaInfo = data.result;
            });
        }
    })
    .controller('debugLoginCtrl', function($scope, $http) {
        $scope.login = function(){
            $http.post('/rest/login/' + $scope.username + '/' + $scope.password, {}, {})
                .success(function (data) {
                    $scope.message = '登陆成功';
                }).error(function (data, status) {
                    $scope.message = "登陆失败" + "/" + data + "/" + status;
                });
        }
    });