page.func="MNG_HOME";
app_vnm.factory('home_service', function ($http) {  
    return { 
    	 
    }
});

app_vnm.factory('downloadFile', ["$http", function ($http) {
	return {
		downloadfile : function () {
			return $http.get("http://localhost:7889/sid",{
				responseType: 'arraybuffer'
			}).success(function(data, status, headers, config) {
			  }).then(function (response, status, headers, config) {
				return response;
			});
		},
	};
}]);

app_vnm.controller('ctrl-home', function ($scope, $rootScope ,$translate,$http, $compile,$timeout, $uibModal, $location, $window, $filter,$localStorage, home_service,downloadFile) {	
	$scope.download = function() { 
		$http({
	        method: 'GET',
	        url: 'http://localhost:7889/sid', 
	        responseType: 'arraybuffer'
	    }).success(function (data, status, headers) {   
	    	 
	        headers = headers();
	        var filename = headers['x-filename'];
	        var contentType = headers['content-type']; 
	        var linkElement = document.createElement('a');
	        try {
	            var blob = new Blob([data], { type: contentType });
	            var url = window.URL.createObjectURL(blob);
	 
	            linkElement.setAttribute('href', url);
	            linkElement.setAttribute("download", filename);
	 
	            var clickEvent = new MouseEvent("click", {
	                "view": window,
	                "bubbles": true,
	                "cancelable": false
	            });
	            linkElement.dispatchEvent(clickEvent);
	        } catch (ex) {
	        }
	    }).error(function (data) {
	    });
		
			};
	//$scope.download();
});
 