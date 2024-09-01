
// To display a preview of an image file selected by the user. It listens for a file input change event,
// retrieves the selected file and uses the FileReader API to read the file as a Data URL.

function previewImage(event) {
    const input = event.target;
    const file = input.files[0];
    const preview = document.getElementById('imagePreview');
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        }
        reader.readAsDataURL(file);
    } else {
        preview.src = '#';
        preview.style.display = 'none';
    }
}

// Bootstrap form validation for forms with the class 'needs-validation'
// When the window loads, it attaches an event listener to each form to check its validity upon submission.

(function () {
    'use strict';
    window.addEventListener('load', function () {
        var forms = document.getElementsByClassName('needs-validation');
        Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();