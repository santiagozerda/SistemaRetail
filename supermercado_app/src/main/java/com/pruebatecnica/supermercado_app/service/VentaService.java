package com.pruebatecnica.supermercado_app.service;

import com.pruebatecnica.supermercado_app.dto.DetalleVentaDTO;
import com.pruebatecnica.supermercado_app.dto.ProductoMasVendidoDTO;
import com.pruebatecnica.supermercado_app.dto.VentaDTO;
import com.pruebatecnica.supermercado_app.exceptions.NotFoundException;
import com.pruebatecnica.supermercado_app.mapper.Mapper;
import com.pruebatecnica.supermercado_app.model.DetalleVentas;
import com.pruebatecnica.supermercado_app.model.EstadoVenta;
import com.pruebatecnica.supermercado_app.model.Producto;
import com.pruebatecnica.supermercado_app.model.Sucursal;
import com.pruebatecnica.supermercado_app.model.TipoPromocion;
import com.pruebatecnica.supermercado_app.model.Venta;
import com.pruebatecnica.supermercado_app.repository.ProductoRepository;
import com.pruebatecnica.supermercado_app.repository.SucursalRepository;
import com.pruebatecnica.supermercado_app.repository.VentaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private SucursalRepository sucuRepo;

    @Autowired
    private ProductoRepository producRepo;

    @Autowired
    private PromocionService promoService;

    @Override
    public List<VentaDTO> getVentas() {

        List<Venta> ventas = ventaRepo.findAll();
        List<VentaDTO> ventDTO = new ArrayList<>();

        //Reemplazamos cada dto y asignamos uno nuevo sin crear una nueva instancia
        VentaDTO dto;
        for (Venta v : ventas) {
            dto = Mapper.toDTO(v);
            ventDTO.add(dto);
        }

        return ventDTO;
    }

    @Override
    public VentaDTO saveVenta(VentaDTO ventaDTO) {

        //Validaciones
        if (ventaDTO == null) {
            throw new NotFoundException("Venta vacia");
        }

        if (ventaDTO.getIdSucursal() == null) {
            throw new RuntimeException("Debe indicar una sucursal");
        }

        if (ventaDTO.getListaDetalle() == null || ventaDTO.getListaDetalle().isEmpty()) {
            throw new RuntimeException("No hay productos para mostrar, debe incluir un producto al menos");
        }

        //Buscamos la Sucursal
        Sucursal sucu = sucuRepo.findById(ventaDTO.getIdSucursal()).orElse(null);
        if (sucu == null) {
            throw new NotFoundException("Sucursal no encontrada");
        }

        //Creamos la venta
        Venta ven = Venta.builder()
                .fechaVenta(LocalDate.now())
                .estado(EstadoVenta.PENDIENTE)
                .sucursal(sucu)
                .build();

        List<DetalleVentas> listaProductos = calcularDetalles(ventaDTO.getListaDetalle(), ven);

        double total = 0;

        for (DetalleVentas det : listaProductos) {
            total += det.getPrecioFinal();
        }

        //Seteamos la lista con los detalles de la venta
        ven.setDetalles(listaProductos);
        ven.setTotal(total);

        //Guardamos en la BD
        ven = ventaRepo.save(ven);

        //Mapeamos la salida
        VentaDTO ventSalida = Mapper.toDTO(ven);

        return ventSalida;
    }

    @Override
    public void deleteVenta(Long id) {

        Venta v = ventaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Este producto no existe o fue eliminado"));
        if (v.getEstado() == EstadoVenta.APROBADA) {
           throw new RuntimeException("No se puede Eliminar una venta que ya fue APROBADA");
        }
        ventaRepo.deleteById(id);
    }

    @Override
    public VentaDTO editVenta(Long id, VentaDTO ventaDTO) {

        //Validamos que la venta exista
        Venta v = ventaRepo.findById(id).orElseThrow(() -> new NotFoundException("Venta no encontrada"));
        
        //Solo se editan ventas aprobadas
        if(v.getEstado() != EstadoVenta.APROBADA)
            throw new NotFoundException("Solo se puede editar ventas APROBADAS");

        //Si existe validamos los datos de la misma
        if (ventaDTO.getFecha() != null) {
            v.setFechaVenta(ventaDTO.getFecha());
        }

        if (ventaDTO.getIdSucursal() != null) {
            Sucursal sucu = sucuRepo.findById(ventaDTO.getIdSucursal())
                    .orElseThrow(()-> new NotFoundException("Sucursal no encontrada"));
            
            v.setSucursal(sucu);
        }

        if (ventaDTO.getListaDetalle() != null) {
            List<DetalleVentas> listaProductos = calcularDetalles(ventaDTO.getListaDetalle(), v);

            double total = 0;

            for (DetalleVentas det : listaProductos) {
                total += det.getPrecioFinal();
            }
            v.setDetalles(listaProductos);
            v.setTotal(total);
        }

        //Guardamos la modificacion de la venta en cuestion
        ventaRepo.save(v);

        //Mapeamos la modificacion al formato DTO
        VentaDTO ventSalida = Mapper.toDTO(v);

        //Mostramos el resultado
        return ventSalida;

    }

    //Sacamos el promedio del producto mas vendido
    @Override
    public ProductoMasVendidoDTO productoMasVendido() {

        //Creamos un auxiliar y buscamos las ventas realizadas
        List<Venta> ventas = ventaRepo.findAll();

        //Validamos que exista ventas
        if (ventas.isEmpty()) {
            throw new RuntimeException("No hay ventas registradas");
        }

        //Hacemos un Map de los productos
        Map<Producto, Long> cantidadProducto = ventas.stream()
                //Aplanamos detalles
                .flatMap(v -> v.getDetalles().stream())
                //Agrupamos por producto y sumamos las cantidades
                .collect(Collectors.groupingBy(DetalleVentas::getProductos,
                        Collectors.summingLong(DetalleVentas::getCantidad)));

        //Validamos que haya productos vendidos para sacar la estadisticas
        if (cantidadProducto.isEmpty()) {
            throw new RuntimeException("No hay productos vendidos");
        }

        //Buscamos el producto con mayor cantidad vendida
        return cantidadProducto.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry
                        -> new ProductoMasVendidoDTO(entry.getKey().getIdProductos(),
                        entry.getKey().getNombre(),
                        entry.getValue(),
                        LocalDate.now())).orElse(null);

    }

    
    //Creacion de los detalles para las ventas
    private List<DetalleVentas> calcularDetalles(List<DetalleVentaDTO> detalleDTO, Venta venta) {

        List<DetalleVentas> detalles = new ArrayList<>();

        for (DetalleVentaDTO detDTO : detalleDTO) {

            Producto p = producRepo.findByIdWithPromo(detDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            //Obtenemos la canitdad de los productos de nuestra lista de detalles de las ventas
            int cantidad = detDTO.getCantidad();
            
            //Obtenemos el precio de los productos
            double precioUnitario = p.getPrecio();

            double precioSinPromo = precioUnitario * cantidad;

            //Llamamos el metodo desde PromocionService
            double totalConPromo = promoService.calcularTotal(p, cantidad);

            double descuento = precioSinPromo - totalConPromo;

            TipoPromocion promoAplicada = descuento > 0
                    ? p.getPromo().getTipo()
                    : TipoPromocion.SIN_PROMOCION;

            //Crear el detalle
            DetalleVentas detVenta = new DetalleVentas();
            detVenta.setVenta(venta);
            detVenta.setProductos(p);
            detVenta.setCantidad(cantidad);
            detVenta.setPrecioUnitario(precioUnitario);
            detVenta.setSubTotal(precioSinPromo);
            detVenta.setPrecioFinal(totalConPromo);
            detVenta.setDescuentoAplicado(descuento);
            detVenta.setPromo(promoAplicada);

            detalles.add(detVenta);
        }

        return detalles;
    }

}
