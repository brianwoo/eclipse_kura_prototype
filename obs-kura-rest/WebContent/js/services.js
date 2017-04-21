'use strict';

angular.module("obs-iot-control-panel")


.service("DeviceFactory", ['$resource', 'baseURL', function($resource, baseURL) {
	
	
	this.getServices = function() {
		
		return $resource("/obs-kura-rest/service", null);
	};
	
	
	this.getDeployService = function() {
		
		return $resource("/obs-kura-rest/deploy", 
				{
					gateway: "@gateway",
					requester: "@requester",
					requestId: "@requestId",
					pkgUrl: "@pkgUrl",
					pkgName: "@pkgName",
					pkgVersion: "@pkgVersion",
				}, 
				{"deploy": {method:"POST"}});
	}
	
	
	this.startService = function() {
		
		return $resource("/obs-kura-rest/service/:bundleId/start", 
				{
					bundleId: "@bundleId",
					gateway: "@gateway",
					requester: "@requester",
					requestId: "@requestId",
				}, 
				{"change": {method:"POST"}});
	};
	
	
	this.stopService = function() {
		
		return $resource("/obs-kura-rest/service/:bundleId/stop", 
				{
					bundleId: "@bundleId",
					gateway: "@gateway",
					requester: "@requester",
					requestId: "@requestId",
				}, 
				{"change": {method:"POST"}});
	};
	
	
}]);