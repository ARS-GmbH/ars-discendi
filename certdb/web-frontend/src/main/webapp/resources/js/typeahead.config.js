/**
 * Konfiguriert den Twitter-Typeahead-Mechanismus.
 */

$(document).ready(function() {
	
	// Setzt den Placeholder auf alle typeahead-Felder, damit Benutzer über Typeahead-Vorhandensein informiert ist.
	if ($('.typeahead').attr('disabled') !== 'disabled') {
		$('.typeahead').attr('placeholder', 'Bitte tippen...');
	}
	
	// Konstruiert die Vorschlags-Engine 'Bloodhound'
	var certSuggestions = new Bloodhound({
		limit: 5,
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: {
			url: '/certdb/certifications/search?q=',
			replace: function(url, query) {
				return url + query;
			}
		}
	});
	 
	// kicks off the loading/processing of `local` and `prefetch`
	certSuggestions.initialize();
	 
	$('.cert-typeahead').typeahead(null, {
		name: 'certifications',
		displayKey: 'name',
		highlight: true,
		source: certSuggestions.ttAdapter()
	});
	
	// Konstruiert die Vorschlags-Engine 'Bloodhound'
	var userSuggestions = new Bloodhound({
		limit: 5,
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: '/certdb/users/search?q=%QUERY'
	});
	 
	// kicks off the loading/processing of `local` and `prefetch`
	userSuggestions.initialize();
	 
	$('.user-typeahead').typeahead(null, {
		name: 'users',
		displayKey: 'name',
		highlight: true,
		source: userSuggestions.ttAdapter()
	});
	
	// Konstruiert die Vorschlags-Engine 'Bloodhound'
	var examSuggestions = new Bloodhound({
		limit: 5,
		datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: '/certdb/exams/search?q=%QUERY'
	});
	 
	// kicks off the loading/processing of `local` and `prefetch`
	examSuggestions.initialize();
	 
	$('.exam-typeahead').typeahead(null, {
		name: 'exams',
		displayKey: 'name',
		highlight: true,
		source: examSuggestions.ttAdapter(),
		templates: {
            empty: '<div style="padding-left: 10px;" class="tt-empty"> ' +
            	'<span style="margin-right: 8px;" class="glyphicon glyphicon-plus-sign"></span>' +
                '<a href="create">Neuen Test anlegen...</a></div>'
        }
	});
	
	configureChosenTypeahead('.chosen-cert-typeahead', "/certdb/certifications/search?q=");
	configureChosenTypeahead('.chosen-exam-typeahead', "/certdb/exams/search?q=");
});

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


