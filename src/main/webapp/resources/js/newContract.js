var compatibleOptions = [];
var incompatibleOptions = [];

var prevSelected = [];
var curSelected = [];

var selectOptionsName = "#selectOptions";

$(document).ready(function () {
    $("#selectTariff").change(optionsUpdated);
    $(selectOptionsName).change(function() {
        var optChanged = optionsChanged(selectOptionsName, prevSelected, curSelected, compatibleOptions, incompatibleOptions);
        prevSelected = optChanged.prevSelected;
        curSelected = optChanged.curSelected;
    });
    optionsUpdated();
});