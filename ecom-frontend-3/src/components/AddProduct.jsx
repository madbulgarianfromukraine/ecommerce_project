import React, { useState } from "react";
import axios from "axios";

const AddProduct = () => {
  const [product, setProduct] = useState({
    name: "",
    brand: "",
    dsc: "",
    price: "",
    category: "",
    stock: "",
    releasedate: "",
    available: false,
  });
  const [image, setImage] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct({ ...product, [name]: value });
  };

  const handleImageChange = (e) => {
    setImage(e.target.files[0]);
    // setProduct({...product, image: e.target.files[0]})
  };

  const submitHandler = (event) => {
    event.preventDefault();

    if (product.name == "" || product.name == null) {
      alert("You must enter a product name!");
      return;
    }
    
    if (product.brand == "" || product.brand == null) {
      product.brand = '-';
    }
    
    if (product.dsc == "" || product.dsc == null) {
      product.dsc = '-';
    }
    //alert("Price: " + product.price);
    if (product.price == "" || product.price == null || parseFloat(product.price) <= 0) {
      alert("Price cannot be empty or zero or negative!");
      //return;
      //product.price = '0.0'; // Use a number, not a string
    }
    
    if (product.category == "" || product.category == null) {
      product.category = 'Other';
    }
    
    // stockQuantity and productAvailable ifs
    if (product.stock == "" || product.stock == null) {
      if(product.available){
        alert("You must enter a stock quantity!");
        return
      }
      product.stock = '0'; // Use a number, not a string
    }
    else if(parseInt(product.stock) < 0){
      alert("Stock quantity cannot be negative!");
      return;
    }
    else if(parseInt(product.stock) == 0 && product.available){
      alert("Stock quantity cannot be zero if product is available!");
      return;
    }
    else if(parseInt(product.stock) > 0 && !product.available){
      alert("Stock quantity cannot be greater than zero if product is not available!");
      return;
    }
    
    // if forgot to specify release date, set it to today
    if (product.releaseDate == "" || product.releaseDate == null) {
  
      if(confirm("Release date not specified. Do you want to set it to today?")){
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd;
        product.releaseDate = today; // Use a valid date format
      }
      else{
        return;
      }
    }

    const formData = new FormData();

    formData.append("imageFile", image);
    formData.append(
      "product",
      new Blob([JSON.stringify(product)], { type: "application/json" })
    );

    axios
      .post("http://localhost:8080/api/product", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        //console.log("Product added successfully:", response.data);
        alert("Product added successfully");
      })
      .catch((error) => {
        //alert("Error adding product: " + toString(product.price));
        //console.error("Error adding product:", error);
        alert("Error adding product");
      });
  };

  return (
    <div className="container">
    <div className="center-container">
      <form className="row g-3 pt-5" onSubmit={submitHandler}>
        <div className="col-md-6">
          <label className="form-label">
            <h6>Name</h6>
          </label>
          <input
            type="text"
            className="form-control"
            placeholder="Product Name"
            onChange={handleInputChange}
            value={product.name}
            name="name"
          />
        </div>
        <div className="col-md-6">
          <label className="form-label">
            <h6>Brand</h6>
          </label>
          <input
            type="text"
            name="brand"
            className="form-control"
            placeholder="Enter your Brand"
            value={product.brand}
            onChange={handleInputChange}
            id="brand"
          />
        </div>
        <div className="col-12">
          <label className="form-label">
            <h6>Description</h6>
          </label>
          <input
            type="text"
            className="form-control"
            placeholder="Add product description"
            value={product.dsc}
            name="dsc"
            onChange={handleInputChange}
            id="description"
          />
        </div>
        <div className="col-5">
          <label className="form-label">
            <h6>Price</h6>
          </label>
          <input
            type="number"
            className="form-control"
            placeholder="Eg: $1000"
            onChange={handleInputChange}
            value={product.price}
            name="price"
            id="price"
          />
        </div>
     
           <div className="col-md-6">
          <label className="form-label">
            <h6>Category</h6>
          </label>
          <select
            className="form-select"
            value={product.category}
            onChange={handleInputChange}
            name="category"
            id="category"
          >
            <option value="">Select category</option>
            <option value="Laptop">Laptop</option>
            <option value="Headphone">Headphone</option>
            <option value="Mobile">Mobile</option>
            <option value="Electronics">Electronics</option>
            <option value="Toys">Toys</option>
            <option value="Fashion">Fashion</option>
            <option value="Other">Other</option>
          </select>
        </div>

        <div className="col-md-4">
          <label className="form-label">
            <h6>Stock Quantity</h6>
          </label>
          <input
            type="number"
            className="form-control"
            placeholder="Stock Remaining"
            onChange={handleInputChange}
            value={product.stock}
            name="stock"
            // value={`${stockAlert}/${stockQuantity}`}
            id="stockQuantity"
          />
        </div>
        <div className="col-md-4">
          <label className="form-label">
            <h6>Release Date</h6>
          </label>
          <input
            type="date"
            className="form-control"
            value={product.releaseDate}
            name="releaseDate"
            onChange={handleInputChange}
            id="releaseDate"
          />
        </div>
        {/* <input className='image-control' type="file" name='file' onChange={(e) => setProduct({...product, image: e.target.files[0]})} />
    <button className="btn btn-primary" >Add Photo</button>  */}
        <div className="col-md-4">
          <label className="form-label">
            <h6>Image</h6>
          </label>
          <input
            className="form-control"
            type="file"
            onChange={handleImageChange}
          />
        </div>
        <div className="col-12">
          <div className="form-check">
            <input
              className="form-check-input"
              type="checkbox"
              name="available"
              id="gridCheck"
              checked={product.available}
              onChange={(e) =>
                setProduct({ ...product, available: e.target.checked })
              }
            />
            <label className="form-check-label">Product Available</label>
          </div>
        </div>
        <div className="col-12">
          <button
            type="submit"
            className="btn btn-primary"
            // onClick={submitHandler}
          >
            Submit
          </button>
        </div>
      </form>
    </div>
    </div>
  );
};

export default AddProduct;
