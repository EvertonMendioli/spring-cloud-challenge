package br.com.caelum.eats.pagamento;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.caelum.eats.pagamento.Pagamento.Status;
import lombok.AllArgsConstructor;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class AtualizaStatus {

	
	
	//private PagamentoRepository pagamentoRepo;
	private PedidoClienteComFeign pedidoCliente;
	
	@HystrixCommand(threadPoolKey = "listarPedidosThreadPool")
	public ResponseEntity<List<PagamentoDto>> listarPedidos(PagamentoRepository pgtoRepository  ) {
		return ResponseEntity.ok(pgtoRepository.findAll()
				.stream()
				.map(PagamentoDto::new)
				.collect(Collectors.toList()));
	}
	
	@HystrixCommand(fallbackMethod = "atualizaStatusPedidoFallBack", threadPoolKey = "atualizaStatusPedidoThreadPool" )
	public Status atualizaStatusPedido(Long pedidoID) {
		
		pedidoCliente.notificaServicoDePedidoParaMudarStatus(pedidoID, new MudancaDeStatusDoPedido("pago"));
		return Pagamento.Status.CONFIRMADO;
	}
	
	public Status atualizaStatusPedidoFallBack(Long pedidoID) {
		return Pagamento.Status.PROCESSANDO;
	}
}
