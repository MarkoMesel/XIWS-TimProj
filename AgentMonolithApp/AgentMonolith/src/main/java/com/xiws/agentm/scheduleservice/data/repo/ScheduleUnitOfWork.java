package com.xiws.agentm.scheduleservice.data.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ScheduleUnitOfWork")
public class ScheduleUnitOfWork {
	private BundleRepo bundleRepo;
	private CarPriceListRepo carPriceListRepo;
	private CommentRepo commentRepo;
	private MessageRepo messageRepo;
	private PriceListRepo priceListRepo;
	private PriceRepo priceRepo;
	private SchedulePublisherTypeRepo publisherTypeRepo;
	private ReservationRepo reservationRepo;
	private ReservationStateRepo reservationStateRepo;
	private UnavailabilityRepo unavailabilityRepo;

	@Autowired
	public ScheduleUnitOfWork(BundleRepo bundleRepo, CarPriceListRepo carPriceListRepo, CommentRepo commentRepo,
			MessageRepo messageRepo, PriceListRepo priceListRepo, PriceRepo priceRepo,
			SchedulePublisherTypeRepo publisherTypeRepo, ReservationRepo reservationRepo,
			ReservationStateRepo reservationStateRepo, UnavailabilityRepo unavailabilityRepo) {
		super();
		this.bundleRepo = bundleRepo;
		this.carPriceListRepo = carPriceListRepo;
		this.commentRepo = commentRepo;
		this.messageRepo = messageRepo;
		this.priceListRepo = priceListRepo;
		this.priceRepo = priceRepo;
		this.publisherTypeRepo = publisherTypeRepo;
		this.reservationRepo = reservationRepo;
		this.reservationStateRepo = reservationStateRepo;
		this.unavailabilityRepo = unavailabilityRepo;
	}

	public BundleRepo getBundleRepo() {
		return bundleRepo;
	}

	public CarPriceListRepo getCarPriceListRepo() {
		return carPriceListRepo;
	}

	public CommentRepo getCommentRepo() {
		return commentRepo;
	}

	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public PriceListRepo getPriceListRepo() {
		return priceListRepo;
	}

	public PriceRepo getPriceRepo() {
		return priceRepo;
	}

	public SchedulePublisherTypeRepo getPublisherTypeRepo() {
		return publisherTypeRepo;
	}

	public ReservationRepo getReservationRepo() {
		return reservationRepo;
	}

	public ReservationStateRepo getReservationStateRepo() {
		return reservationStateRepo;
	}

	public UnavailabilityRepo getUnavailabilityRepo() {
		return unavailabilityRepo;
	}
}
