package br.com.caelum.eats.pagamento;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.caelum.eats.pagamento.Pagamento.Status;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class AtualizaStatus {

	
	
	
	private PedidoClienteComFeign pedidoCliente;
	
	@HystrixCommand(fallbackMethod = "atualizaStatusPedidoFallBack" )
	public Status atualizaStatusPedido(Long pedidoID) {
		
		pedidoCliente.notificaServicoDePedidoParaMudarStatus(pedidoID, new MudancaDeStatusDoPedido("pago"));
		return Pagamento.Status.CONFIRMADO;
	}
	
	public Status atualizaStatusPedidoFallBack(Long pedidoID) {
		return Pagamento.Status.PROCESSANDO;
	}
}
