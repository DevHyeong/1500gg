//import path from 'path';
//import HtmlWebpackPlugin from 'html-webpack-plugin';
//import {CleanWebpackPlugin} from 'clean-webpack-plugin';


const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const {CleanWebpackPlugin} = require("clean-webpack-plugin");
//const __dirname = path.resolve();
const outputDirectory = "dist";
 
module.exports = {
  entry: "./src/js/index.js",
  output: {
    path: path.join(__dirname, outputDirectory),
    filename: "bundle.js",
    publicPath: "/"
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader"
        }
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader","postcss-loader"]
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[path][name].[ext]',
            },
          },
        ],
      },
    ]
  },
  devServer: {
    port: 3000,
    open: true,
    historyApiFallback: true,
    proxy: {
      "/api": "http://localhost:1234"
    }
  },
  plugins: [
    new CleanWebpackPlugin({
        cleanAfterEveryBuildPatterns : [outputDirectory]
    }),
    new HtmlWebpackPlugin({
      template: "./src/html/index.html"
    })
    
  ]
};
