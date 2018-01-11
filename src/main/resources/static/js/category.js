function displayCategoryList(action) {
	$.ajax({
		url : action,
		type : 'GET',
		success : function(data) {
			var $categoryList = $('#categoryList');

			for (var i = 0; i < data.length; i++) {
				var li = document.createElement('li');

				li.appendChild(createSpan(data[i].name));

				var updateUrl = 'javascript:updateModalCategory(event, ' + data[i].id + ')';
				li.appendChild(createImageLink(updateUrl, 'fa fa-pencil'));

				var deleteUrl = 'javascript:deleteCategory(event, ' + data[i].id + ')';
				li.appendChild(createImageLink(deleteUrl, 'fa fa-remove'));

				$categoryList.append(li);
			}
		},
		error : function(response) {
			console.log('Error in displayCategoryList');
		}
	});
}

function deleteCategory(e, id) {
	if (e) {
		e.preventDefault();
	}
	
	var action = 'rest/restcategory/' + id;

	// Put in utils.js
	// var token = $("meta[name='_csrf']").attr("content");
	// var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({
		url : action,
		type : 'DELETE',
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(response) {
			window.location.href = "/category";
		},
		error : function(response) {
			console.log('Error in deleteCategory with id=', id);
		}

	});
	
	if (e) {
		return false;
	}
}

function createModalCategory(e) {
	e.preventDefault();
	
	var action = 'category/create';
	$.ajax({
		url : action,
		type : 'GET',
		success : function(response) {
			$('#categoryModalHolder').html(response);
			$('#categoryModal').modal('show');
		},
		error : function(response) {
			console.log('Error in editCategory with id=', id);
		}
	});
	
	return false;
}

function updateModalCategory(e, id) {
	if (e) {
		e.preventDefault();
	}
	
	var action = 'category/update/' + id;
	$.ajax({
		url : action,
		type : 'GET',
		success : function(response) {
			$('#categoryModalHolder').html(response);
			$('#categoryModal').modal('show');
		},
		error : function(response) {
			console.log('Error in editCategory with id=', id);
		}
	});
	
	if (e) {
		return false;
	}
}

function submitModalCategory(e, id) {
	e.preventDefault();

	var $form = $('#categoryModalForm'),
		data = {},
		action, method;
	
	$form.serializeArray().map(function(input) {
		if (input.name !== '_csrf') {
			data[input.name] = input.value;
		}
	});

	if (id === null) {
		action = 'rest/restcategory';
		method = 'POST';
	}
	else {
		action = 'rest/restcategory/' + id;
		method = 'PUT';
	}
	
	$.ajax({
		url: action,
		type: method,
		contentType : 'application/json; charset=utf-8',
		mimeType : 'application/json',
		dataType: 'json',
		data: JSON.stringify(data),
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			window.location.href = "/category";
		},
		error: function(response) {
			$('#err-' + response.responseJSON.errors[0].field).html(response.responseJSON.errors[0].defaultMessage)
			console.log('Error in submitModalCategory with id=', id);
		}
	});
	
	return false;
}

$(document).ready(function() {
	displayCategoryList('rest/restcategory');
});
