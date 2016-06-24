
$(document).ready(function(){
	
    initializeKnowledgeTree();
    initializeChosen();
    wholeTableRowClickable();
    initializeToolTip();
    initialzieDatepicker();
    initBtnAddKnowledgeRow();
});

var initBtnAddKnowledgeRow = function() {
	var clonedRow;
	$("#addKnowledgeRow").click(function() {
	if (!clonedRow) {
	      var clonedRow = $("#knowledgeTable tr:first").clone();
	}
	var newRow = clonedRow.clone();
	$("#knowledgeTable tr:last").after(newRow);
	newRow.find("td > input").val('0');
	
	var tableLength = $("#knowledgeTable tr").length;
	
	var selectAttributeId = "skillProofEdges" + (tableLength-1) + ".skill";
	var selectAttributeName = "skillProofEdges[" + (tableLength-1) + "].skill";
	newRow.find("td > select").attr("id", selectAttributeId).attr("name", selectAttributeName);
	
	var inputAttributeId = "skillProofEdges" + (tableLength-1) + ".hours";
	var inputAttributeName = "skillProofEdges[" + (tableLength-1) + "].hours";
	newRow.find("td > input").attr("id", inputAttributeId).attr("name", inputAttributeName);
	
//	correctIndices(); //TODO
	$(window).trigger('resize');
	});
//	registerDeleteButtonsClickHandler();
} 

var initializeKnowledgeTree = function() {
	$('#knowledgeTree').jstree({
		'core' : {
			'data' : {
				url : function(node) {
					return window.location.href + '/ajax';
				},
				type : "GET",
				dataType : "json",
				data : function(node) {
					return {
						id : node.id
					};
				},
			}
		},
		"plugins" : [ "sort" ]
	});
}

var initializeChosen = function() {
	 $(document).tooltip();
	$(".chosen-select").chosen({
		single_backstroke_delete:"false",
		placeholder_text_multiple:"please select",
		placeholder_text_single:"please select",
		search_contains:"true"
			});
}

var wholeTableRowClickable = function() {
	$('table tr[data-href]').click(function(){
        window.location = $(this).data('href');
        return false;
    });
}

var initializeToolTip = function() {
	$(function() {
		$('[data-toggle="tooltip"]').tooltip();
	});
}

var initialzieDatepicker = function() {
	$('.datepicker').datepicker({
		format: "dd.mm.yyyy",
		weekStart: 1,
		todayBtn: true,
		language: "de"
	});
}

var sidebarSize = function() {
    var min_width;
    if (Modernizr.mq('(min-width: 0px)')) {
        min_width = function(width) {
            return Modernizr.mq('(min-width: ' + width + 'px)');
        };
    }
    else {
        min_width = function(width) {
            return $(window).width() >= width;
        };
    }
    
    if (min_width(768)) {
        var heightToSet = $("#content-frame").outerHeight()
        $("#sidebar-frame").css("min-height", heightToSet);
        $(".sidebar").css("min-height", heightToSet);
    }
    else {
        $("#sidebar-frame").css("min-height", "auto");
        $(".sidebar").css("min-height", "auto");
    }
}

var contentSize = function() {
    var siteHeight = $(window).height();
    var headerHeight = $("#header").outerHeight();
    var footerHeight = $("#footer").outerHeight();
    var paddingString = $("#content-frame").css("paddingTop").replace(/[^-\d\.]/g, '');
    var padding = parseInt(paddingString);
    
    var resultHeight = siteHeight - headerHeight - footerHeight - 2*padding;
    
    $("#inner-content-frame").css("min-height", resultHeight);
    $("#content-frame").css("min-height", resultHeight + 2*padding);
}

var innerContentSize = function() {
    if ($("#inner-content-frame").height() <= parseInt($("#content-frame").css("min-height").replace(/[^-\d\.]/g, ''))) {
        $("#inner-content-frame").css("min-height", $("#content-frame").height());
    }
}

var sizeChanger = function() {
    contentSize();
    sidebarSize();
    innerContentSize();
};

$(document).ready(sizeChanger);
$(window).on('resize', sizeChanger);