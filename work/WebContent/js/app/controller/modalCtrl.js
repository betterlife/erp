function modalInstanceCtrl ($scope, $modalInstance, modalData) {
    $scope.modalData = modalData;
    $scope.id = modalData.id;
    $scope.ok = function () {
        $modalInstance.close($scope.id);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}