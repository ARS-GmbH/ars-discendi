var formFields = ["necessaryExams", "choosableExams", "necessaryCertifications"];

/**
 * Konfiguriert die Seite zur Eingabe der Zertifizierungs-Pfade.
 */
$(document).ready(function() {
	var clonedPanel;
	$("#addPath").click(function() {
		if (!clonedPanel) {
			var clonedPanel = $("#pathListCommand > .panel").first().clone();
		}
		var newPanel = clonedPanel.clone();
		$("#pathListCommand > .panel").last().after(newPanel);
		newPanel.find(".form-group > .col-sm-8").empty();
		newPanel.find(".form-group > .col-sm-8").each(function(i) {
			var newSelect = $('<select id="' + formFields[i] + '" name="' + formFields[i] + '" multiple="multiple" style="display: none;"></select>');
			$(this).append(newSelect);
			newSelect.chosen({width: "100%"});
		})
		correctIndices();
		configureTypeaheadOneByOne();
		$(window).trigger('resize');
	});
	configureTypeaheadOneByOne();
	registerDeleteButtonsClickHandler();
});

var correctIndices = function() {
	$("#pathListCommand > .panel").each(function(index) {
		var panelHeading = $(this).find(" > .panel-heading");
		panelHeading.text("Pfad " + (index + 1));
		panelHeading.attr("id", "path[" + index + "]");
		if (index !== 0) {
			var deleteBtn = $('<button id="deletePath[' + index +']" type="button" class="btn btn-danger btn-sm pull-right" style="margin-top: -5px;">Pfad entfernen</button>');
			panelHeading.append(deleteBtn);
			registerDeleteButtonsClickHandler();
		}
		var formWrap = $(this);
		$.each(formFields, function(j, value) {
			formWrap.find('select[id*="' + value + '"]').attr("id", ("paths" + index + "." + value));
			formWrap.find('select[id*="' + value + '"]').attr("name", ("paths[" + index + "]." + value));
			
			if (value === "necessaryExams") {
				formWrap.find('select[id*="' + value + '"]').attr("class", ("chosen-select chosen-exam-typeahead-" + index));
			}
			else if (value === "choosableExams") {
				formWrap.find('select[id*="' + value + '"]').attr("class", ("chosen-select chosen-ch-exam-typeahead-" + index));
			}
			else {
				formWrap.find('select[id*="' + value + '"]').attr("class", ("chosen-select chosen-cert-typeahead-" + index));
			}
			formWrap.find('div[id*="_' + value + '_chosen"]').attr("id", "paths" + index + "_" + value + "_chosen");
			formWrap.find('input[name*="' + value + '"]').attr("name", ("_paths[" + index + "]." + value));
		});
	});
};

var registerDeleteButtonsClickHandler = function() {
	$('button[id*="deletePath"]').click(function() {
		var button = $(this);
		bootbox.dialog({
			message : "Bist Du sicher, dass du diesen Pfad entfernen willst?",
			buttons : {
				danger : {
					label : "Nein! Tu's nicht!",
					className : "btn-danger",
				},
				main : {
					label : "Hau ihn weg!",
					className : "btn-default",
					callback : function() {
						button.parent().parent().remove();
						correctIndices();
						$(window).trigger('resize');
					}
				}
			}
		});
	});
};

var configureTypeaheadOneByOne = function() {
	$("#pathListCommand > .panel").each(function(index) {
		configureChosenTypeahead(('.chosen-cert-typeahead-' + index), "/certdb/certifications/search?q=");
		configureChosenTypeahead(('.chosen-exam-typeahead-' + index), "/certdb/exams/search?q=");
		configureChosenTypeahead(('.chosen-ch-exam-typeahead-' + index), "/certdb/exams/search?q=");
	});
}

/**
 * Vereint das Chosen-Plugin mit einem Typeahead-Mechanismus.
 */
var configureChosenTypeahead = function(cssClass, url) {
	$((cssClass + ' + .chosen-container-multi .chosen-choices input')).autocomplete({
		  delay: 1000,
		  source: function( request, response ) {
		    $.ajax({
		      url: url + request.term,
		      dataType: "json",
		      beforeSend: function(){},
		      success: function( data ) {
		        response( $.map( data, function( item ) {
		          if (!$(cssClass + ' > option[value="' + item.name + '"]').length) {
		        	  $('select' + cssClass).prepend('<option value="' + item.name + '">' + item.name + '</option>');
		          }
		          var searchFieldValue = $((cssClass + ' + .chosen-container-multi .chosen-choices input')).val();
		          $('select' + cssClass).trigger("chosen:updated");
		          $((cssClass + ' + .chosen-container-multi .chosen-choices input')).val(searchFieldValue).css('width', '100%');
		        }));
		      }
		    });
		  }
	});
};