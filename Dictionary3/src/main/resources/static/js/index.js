const contentSearchBox = document.getElementById("boxSearching");

/*window.addEventListener('beforeunload', (event) => {
  // Cancel the event as stated by the standard.
  event.preventDefault();
  // Chrome requires returnValue to be set.
  event.returnValue = '';
});*/

contentSearchBox.addEventListener("change", () => {
	window.location.replace(`/search/${contentSearchBox.value}`);
})

function buscar(word){
	window.location.replace(`/search/+${word}`);
}

