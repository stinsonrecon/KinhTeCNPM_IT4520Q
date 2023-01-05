var appModule = angular.module('ivh.dropdown', ['ivh.treeview']);

appModule.directive('ivhDropdown',['$document', function($document){

    return {
        restrict: 'E',
        scope: {
            ivhModel: '=',
            itemTreeSelected: '=',
            labelTree : '=',
            valueTree : '='
        },
        templateUrl: '/template/ivh-dropDown',
        replace: true,
        link: function(scope, element, attr){
            scope.$watch("ivhModel", function(){
                scope.changeCallback();
            }, true);
            scope.onClickShowHideIVHDropdown = function(e){
                if(e.target.classList[0] === "dropDown" && e.target.parentElement.children.length === 2 && e.target.parentElement.children[1].classList[0] === "dropDownContent"){
                    e.target.parentElement.children[1].classList.toggle("show");
                }
                else if(e.target.classList[0] === "dropDownButton" && e.target.parentElement.parentElement.children.length === 2 && e.target.parentElement.parentElement.children[1].classList[0] === "dropDownContent")
                {
                    e.target.parentElement.parentElement.children[1].classList.toggle("show");
                }
                else if(e.target.classList[0] === "dropDownButtonIcon" && e.target.parentElement.parentElement.parentElement.children.length === 2 && e.target.parentElement.parentElement.parentElement.children[1].classList[0] === "dropDownContent")
                {
                    e.target.parentElement.parentElement.parentElement.children[1].classList.toggle("show");
                }
                e.stopPropagation();
            };

            scope.changeCallback = function(node){
                var selectedItm = getSelectedFormIVHTreeModel(scope.ivhModel);
                let valueTreeSelected = [];
                if(selectedItm.length > 0)
                {
                    if(selectedItm.length === 1){
                        scope.selectedText = selectedItm[0][scope.labelTree];
                    }else{
                        scope.selectedText = selectedItm[0][scope.labelTree] + ' and ' + (selectedItm.length -1) + ' other(s)';
                    }
                    selectedItm.forEach(item => {
                        valueTreeSelected.push(item[scope.valueTree])
                    })
                }
                else{
                    scope.selectedText = "";
                    valueTreeSelected = [];
                }
                scope.itemTreeSelected(valueTreeSelected);
            };

            $document.bind('click', function(event){
                var isClickedElementChildOfPopup = element.find(event.target).length > 0;
                if(!isClickedElementChildOfPopup && element[0].children[1].classList.contains("show")){
                    element[0].children[1].classList.toggle("show");
                }
            });

            var setZindex = function(){
                var zIndexDropDownButton = parseInt(element[0].children[0].style.zIndex === '' ? 0 : element[0].children[0].style.zIndex);
                element[0].children[0].style.zIndex = zIndexDropDownButton;
                element[0].children[0].children[0].style.zIndex = zIndexDropDownButton + 1;
                element[0].children[1].style.zIndex = zIndexDropDownButton + 2;
            };

            setZindex();

            var getSelectedFormIVHTreeModel = function(ivhModel){
                var selectedItems = [];
                angular.forEach(ivhModel, function(value){
                    if(value.selected){
                        selectedItems.push(value);
                    }
                    if(angular.isDefined(value.children) && value.children?.length > 0 && value.children != null)
                    {
                        angular.forEach(getSelectedFormIVHTreeModel(value.children), function(vle){
                            selectedItems.push(vle);
                        });
                    }
                });
                return selectedItems;
            };
            let initTreeDropDown = () => {
                scope.changeCallback();
            }
            initTreeDropDown();
        }
    };
}]);
