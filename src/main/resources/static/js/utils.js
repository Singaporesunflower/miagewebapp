var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function createSpan(innerHTML) {
	var newSpan = document.createElement('span');
	newSpan.innerHTML = innerHTML;
	return newSpan;
}

function createLink(href, innerNode) {
	var newLink = document.createElement('a');
	newLink.setAttribute('href', href);
	newLink.appendChild(innerNode);
	return newLink;
}

function createImageLink(href, imageClass) {
	var image = document.createElement('i');
	image.setAttribute('class', imageClass);
	return createLink(href, image);
}