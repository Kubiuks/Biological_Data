<?php

    define('ROOT', dirname(__FILE__).'/');
    $store_path = ROOT.'../static/images'.basename($_FILES['file']['name']);

//    move_uploaded_file($_FILES['file'] ['temp_name'], 'uploaded_images/'.$_FILES['file']['name']);
    (move_uploaded_file($_FILES['file'] ['temp_name'], $store_path));
    echo "uploaded.";
?>