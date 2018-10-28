app.directive('viewOrder', function() {
	   //define the directive object
	   var directive = {};
	   
	   //restrict = E, signifies that directive is Element directive
	   directive.restrict = 'E';
	   
	   //template replaces the complete element with its text. 
	   directive.templateUrl = "/views/order/ViewOrder.html";
	   
	   //scope is used to distinguish each student element based on criteria.
	   //directive.scope = {
	   //   student : "=name"
	   //}
	   
	   //compile is called during application initialization. AngularJS calls it once when html page is loaded.
		
	   //directive.compile = function(element, attributes) {
	    //  element.css("border", "1px solid #cccccc");
	      
	      //linkFunction is linked with each element with scope to get the element specific data.
	     // var linkFunction = function($scope, element, attributes) {
	     //    element.html("Student: <b>"+$scope.student.name +"</b> , Roll No: <b>"+$scope.student.rollno+"</b><br/>");
	     //    element.css("background-color", "#ff00ff");
	     // }
	     // return linkFunction;
	   // }
	   return directive;
	});

app.directive('createOrder', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/order/CreateOrder.html";
	   
	   return directive;
	});

app.directive('salesReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/SalesReport.html";
	   
	   return directive;
	});

app.directive('receiptReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/ReceiptReport.html";
	   
	   return directive;
	});

app.directive('stockWiseReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/StockWiseReport.html";
	   
	   return directive;
	});

app.directive('partyWiseReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/PartyWiseReport.html";
	   
	   return directive;
	});

app.directive('addProducts', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/product/AddProducts.html";
	   
	   return directive;
	});

app.directive('viewProducts', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/product/ViewProducts.html";
	   
	   return directive;
	});

app.directive('addCustomer', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/customer/AddCustomer.html";
	   
	   return directive;
	});

app.directive('viewCustomers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/customer/ViewCustomers.html";
	   
	   return directive;
	});

app.directive('editCustomers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/customer/EditCustomers.html";
	   
	   return directive;
	});

app.directive('addUser', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/user/AddUser.html";
	   
	   return directive;
	});

app.directive('viewUsers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/user/ViewUsers.html";
	   
	   return directive;
	});

app.directive('pwCheck', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
          var firstPassword = '#' + attrs.pwCheck;
          elem.add(firstPassword).on('keyup', function () {
            scope.$apply(function () {
              var v = elem.val()===$(firstPassword).val();
              ctrl.$setValidity('pwmatch', v);
            });
          });
        }
      }
    }]);
/**********
The following directives are necessary in order to track dirty state and validity of the rows 
in the table as the user pages within the grid
------------------------
*/

(function() {
app.directive("demoTrackedTable", demoTrackedTable);

demoTrackedTable.$inject = [];

function demoTrackedTable() {
  return {
    restrict: "A",
    priority: -1,
    require: "ngForm",
    controller: demoTrackedTableController
  };
}

demoTrackedTableController.$inject = ["$scope", "$parse", "$attrs", "$element"];

function demoTrackedTableController($scope, $parse, $attrs, $element) {
  var self = this;
  var tableForm = $element.controller("form");
  var dirtyCellsByRow = [];
  var invalidCellsByRow = [];

  init();

  ////////

  function init() {
    var setter = $parse($attrs.demoTrackedTable).assign;
    setter($scope, self);
    $scope.$on("$destroy", function() {
      setter(null);
    });

    self.reset = reset;
    self.isCellDirty = isCellDirty;
    self.setCellDirty = setCellDirty;
    self.setCellInvalid = setCellInvalid;
    self.untrack = untrack;
  }

  function getCellsForRow(row, cellsByRow) {
    return _.find(cellsByRow, function(entry) {
      return entry.row === row;
    })
  }

  function isCellDirty(row, cell) {
    var rowCells = getCellsForRow(row, dirtyCellsByRow);
    return rowCells && rowCells.cells.indexOf(cell) !== -1;
  }

  function reset() {
    dirtyCellsByRow = [];
    invalidCellsByRow = [];
    setInvalid(false);
  }

  function setCellDirty(row, cell, isDirty) {
    setCellStatus(row, cell, isDirty, dirtyCellsByRow);
  }

  function setCellInvalid(row, cell, isInvalid) {
    setCellStatus(row, cell, isInvalid, invalidCellsByRow);
    setInvalid(invalidCellsByRow.length > 0);
  }

  function setCellStatus(row, cell, value, cellsByRow) {
    var rowCells = getCellsForRow(row, cellsByRow);
    if (!rowCells && !value) {
      return;
    }

    if (value) {
      if (!rowCells) {
        rowCells = {
          row: row,
          cells: []
        };
        cellsByRow.push(rowCells);
      }
      if (rowCells.cells.indexOf(cell) === -1) {
        rowCells.cells.push(cell);
      }
    } else {
      _.remove(rowCells.cells, function(item) {
        return cell === item;
      });
      if (rowCells.cells.length === 0) {
        _.remove(cellsByRow, function(item) {
          return rowCells === item;
        });
      }
    }
  }

  function setInvalid(isInvalid) {
    self.$invalid = isInvalid;
    self.$valid = !isInvalid;
  }

  function untrack(row) {
    _.remove(invalidCellsByRow, function(item) {
      return item.row === row;
    });
    _.remove(dirtyCellsByRow, function(item) {
      return item.row === row;
    });
    setInvalid(invalidCellsByRow.length > 0);
  }
}
})();

(function() {
app.directive("demoTrackedTableRow", demoTrackedTableRow);

demoTrackedTableRow.$inject = [];

function demoTrackedTableRow() {
  return {
    restrict: "A",
    priority: -1,
    require: ["^demoTrackedTable", "ngForm"],
    controller: demoTrackedTableRowController
  };
}

demoTrackedTableRowController.$inject = ["$attrs", "$element", "$parse", "$scope"];

function demoTrackedTableRowController($attrs, $element, $parse, $scope) {
  var self = this;
  var row = $parse($attrs.demoTrackedTableRow)($scope);
  var rowFormCtrl = $element.controller("form");
  var trackedTableCtrl = $element.controller("demoTrackedTable");

  self.isCellDirty = isCellDirty;
  self.setCellDirty = setCellDirty;
  self.setCellInvalid = setCellInvalid;

  function isCellDirty(cell) {
    return trackedTableCtrl.isCellDirty(row, cell);
  }

  function setCellDirty(cell, isDirty) {
    trackedTableCtrl.setCellDirty(row, cell, isDirty)
  }

  function setCellInvalid(cell, isInvalid) {
    trackedTableCtrl.setCellInvalid(row, cell, isInvalid)
  }
}
})();

(function() {
app.directive("demoTrackedTableCell", demoTrackedTableCell);

demoTrackedTableCell.$inject = [];

function demoTrackedTableCell() {
  return {
    restrict: "A",
    priority: -1,
    scope: true,
    require: ["^demoTrackedTableRow", "ngForm"],
    controller: demoTrackedTableCellController
  };
}

demoTrackedTableCellController.$inject = ["$attrs", "$element", "$scope"];

function demoTrackedTableCellController($attrs, $element, $scope) {
  var self = this;
  var cellFormCtrl = $element.controller("form");
  var cellName = cellFormCtrl.$name;
  var trackedTableRowCtrl = $element.controller("demoTrackedTableRow");

  if (trackedTableRowCtrl.isCellDirty(cellName)) {
    cellFormCtrl.$setDirty();
  } else {
    cellFormCtrl.$setPristine();
  }
  // note: we don't have to force setting validity as angular will run validations
  // when we page back to a row that contains invalid data

  $scope.$watch(function() {
    return cellFormCtrl.$dirty;
  }, function(newValue, oldValue) {
    if (newValue === oldValue) return;

    trackedTableRowCtrl.setCellDirty(cellName, newValue);
  });

  $scope.$watch(function() {
    return cellFormCtrl.$invalid;
  }, function(newValue, oldValue) {
    if (newValue === oldValue) return;

    trackedTableRowCtrl.setCellInvalid(cellName, newValue);
  });
}
})();