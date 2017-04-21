'use strict';


angular.module("obs-iot-control-panel")

.controller("DeviceCtrl", 
		    ['$scope', "DeviceFactory", "$timeout",
		    function($scope, DeviceFactory, $timeout) {
	
	$scope.testMsg = "Brian Test";
	
	$scope.gateway = "08:00:27:52:DC:FC";
	$scope.requester = "BrianWoo";
	$scope.requestId = "brian-woo-12345";
	
	$scope.listOfServices = [];
	
	$scope.downloadPkgs = [];
	
	
	$scope.updatePackage = function(bundleId) {
		
		console.log($scope.downloadPkgs[bundleId].pkgUrl);
		console.log($scope.downloadPkgs[bundleId].pkgName);
		console.log($scope.downloadPkgs[bundleId].pkgVersion);
		
		var deployParams = {
				gateway: $scope.gateway,
				requester: $scope.requester,
				requestId: $scope.requestId,
				pkgUrl: $scope.downloadPkgs[bundleId].pkgUrl,
				pkgName: $scope.downloadPkgs[bundleId].pkgName,
				pkgVersion: $scope.downloadPkgs[bundleId].pkgVersion
		};
		
		console.log("Deploy Package...");
		
		DeviceFactory.getDeployService()
		.deploy(deployParams).$promise
		
		.then(function(response) {
			console.log(response);
			
			$timeout(function() {
				// Update services list
				$scope.listServices();				
			}, 1500);
			
		});	
	};
	
	
	
	/**
	 * Show list of services
	 */
	$scope.listServices = function() {
		console.log($scope.gateway);
		console.log($scope.requester);
		console.log($scope.requestId);
		
		
		DeviceFactory.getServices().get({
			gateway: $scope.gateway,
			requester: $scope.requester,
			requestId: $scope.requestId
		})
		.$promise.then(function(response) {
			console.log("get list of services");
			//console.log(response.body);
			$scope.listOfServices = JSON.parse(response.body);
			
		}, function(error) {
			
			console.log(error);
		});
	};
		
		
	$scope.isActive = function(state) {
		
		return (state === 'ACTIVE');
	};
	
	
	/**
	 * Start Service
	 */
	$scope.startService = function(bundleId) {
		var onOffParams = {
				bundleId: bundleId,
				gateway: $scope.gateway,
				requester: $scope.requester,
				requestId: $scope.requestId
		};
		
		console.log("turning it on");
		
		DeviceFactory.startService()
		.change(onOffParams).$promise
		
		.then(function(response) {
			console.log(response);
			// Update services list
			$scope.listServices();
			
		});		
	};
	
	
	
	/**
	 * Stop Service
	 */
	$scope.stopService = function(bundleId) {
		var onOffParams = {
				bundleId: bundleId,
				gateway: $scope.gateway,
				requester: $scope.requester,
				requestId: $scope.requestId
		};
		
		console.log("will turn off now ");
		
		DeviceFactory.stopService()
		.change(onOffParams).$promise
		
		.then(function(response) {
			console.log(response);
			
			// Update services list
			$scope.listServices();
			
		});		
	};
	
		
}])



;