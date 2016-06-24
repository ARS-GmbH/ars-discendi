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
$("*").on('resize', sizeChanger);

$(document).ready(function() {
    $('a[data-toggle="collapse"]').on("click", function() {
        if ($(this).hasClass("collapsed")) {
            $(this).children().removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-down");
        }
        else {
            $(this).children().removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-right");
        }
    });
});