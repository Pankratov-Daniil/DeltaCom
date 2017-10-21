var compatibleOptions = [];
var incompatibleOptions = [];

var prevSelected = [];
var curSelected = [];

var options = [];

var selectOptionsName = "#selectOptions";

function saveAllOptions(allOptions) {
    options = allOptions;
    $("#selectTariff").change(optionsUpdated);
    $(selectOptionsName).change(function() {
        var optChanged = optionsChanged(selectOptionsName, prevSelected, curSelected, compatibleOptions, incompatibleOptions);
        prevSelected = optChanged.prevSelected;
        curSelected = optChanged.curSelected;
    });
    optionsUpdated();
}

$(document).ready(function () {
    getAllOptions(saveAllOptions);
});