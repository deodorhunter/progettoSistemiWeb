//$scope=model

var cartApp = angular.module("cartApp", []);

cartApp.controller("cartCtrl", function ($scope, $http){

    $scope.refreshCart = function () {
        $http.get('/rest/cart/'+$scope.cartId).success(function (data) {
            $scope.cart=data;
        });
    };


    $scope.increaseQuantity = function (quantity, productId) {
        quantity++;
        $http.get('/rest/cart/validate/' + quantity + '/' + productId).success( function(validate){
            if(validate==="true"){

                $http.put('/rest/cart/update/' + productId +'/'+ quantity).success( function(){

                    alert("Product quantity successfully updated!");
                    $scope.refreshCart();
                });
            }
            else alert("Product quantity exceeds stock!");
        });
    };

    $scope.decreaseQuantity = function (quantity, productId) {
        if(quantity>1){
            quantity--;
            $http.put('/rest/cart/update/' + productId +'/'+ quantity).success( function(){

                // alert("Product quantity successfully updated!");
                $scope.refreshCart();
            });
        }
    };
    
    $scope.clearCart = function () {
        $http.delete('/rest/cart/'+$scope.cartId).success($scope.refreshCart());
    };

    $scope.initCartId = function (cartId) {
        $scope.cartId = cartId;
        $scope.refreshCart(cartId);
    };

    $scope.addToCart = function (productId) {
        $http.put('/rest/cart/add/'+ productId).success(function () {
            console.log($scope.cart);
            alert("Product successfully added to the cart!")
        });
    };

    $scope.removeFromCart = function (productId) {
        $http.put('/rest/cart/remove/'+ productId).success(function (data) {
            $scope.refreshCart();
        });
    };

    $scope.calGrandTotal = function () {
        if($scope.cart != null){
            var grandTotal=0;

            for (var i=0; i<$scope.cart.cartItems.length; i++) {
                grandTotal+=$scope.cart.cartItems[i].total;
            }
        }

        return grandTotal;
    };
});