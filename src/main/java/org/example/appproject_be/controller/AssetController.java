package org.example.appproject_be.controller;

import jakarta.validation.Valid;
import org.example.appproject_be.model.Asset;
import org.example.appproject_be.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController //@Controller + @ResponseBody
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class AssetController {
    @Autowired
    private AssetService assetService;
    //localhost:8080/assets
    @GetMapping("/assets") //@RequestMapping(value = "/assets", method = RequestMethod.GET)
    public List<Asset> getAssets(){
        return assetService.getAssets();
    }
    //localhost:8080/assets/index
    @GetMapping("/assets/{index}")
    public Optional<Asset> getAsset(@PathVariable("index") Long index){
        return assetService.getAsset(index);
    }
    @PostMapping("/assets")
    public Asset saveAsset(@Valid @RequestBody Asset asset){
        return assetService.saveAsset(asset);
    }
    @PutMapping("/assets/{index}")
    public Asset updateAsset (@PathVariable Long index, @RequestBody Asset asset){
        System.out.println("updating the asset data for the index " + index);
        return asset;
    }
    //localhost:8080/assets?index=index123
    @DeleteMapping("/assets")
    public void deleteAsset (@RequestParam("idx") Long idx){
        assetService.deleteAsset(idx);
//        return "Deleting the asset details for the assetCode " + assetCode;
    }
}
